package com.image2ascii;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 100;

    private Button selectImageButton;
    private Button convertButton;
    private TextView asciiTextView;
    private String selectedImagePath = "";

    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    selectedImagePath = getRealPathFromURI(selectedImageUri);

                    if (selectedImagePath != null) {
                        convertButton.setEnabled(true);
                        Toast.makeText(this, "画像を選択しました", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "画像のパスを取得できません", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectImageButton = findViewById(R.id.selectImageButton);
        convertButton = findViewById(R.id.convertButton);
        asciiTextView = findViewById(R.id.asciiTextView);

        if (!hasStoragePermission()) {
            requestStoragePermission();
        }

        selectImageButton.setOnClickListener(v -> {
            if (hasStoragePermission()) {
                pickImageFromStorage();
            } else {
                Toast.makeText(this, "ストレージアクセス権限が必要です", Toast.LENGTH_SHORT).show();
                requestStoragePermission();
            }
        });

        convertButton.setOnClickListener(v -> {
            if (!selectedImagePath.isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                if (bitmap != null) {
                    String asciiArt = convertToAscii(bitmap);
                    asciiTextView.setText(asciiArt);
                    saveAsciiArt(asciiArt);
                } else {
                    Toast.makeText(this, "画像を読み込めません", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 権限があるか確認
    private boolean hasStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    // 権限をリクエスト
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_REQUEST_CODE
        );
    }

    // 権限リクエストの結果を処理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "ストレージアクセス権限が許可されました", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "ストレージアクセス権限が必要です", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ストレージから画像を選択
    private void pickImageFromStorage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        pickImageLauncher.launch(intent);
    }

    // URIから実際のパスを取得
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(column_index);
            }
        }
        return null;
    }

    // アスキーアートに変換
    private String convertToAscii(Bitmap bitmap) {
        StringBuilder asciiArt = new StringBuilder();
        int targetWidth = 200; // アスキーアートの幅を調整
        int targetHeight = (int) ((double) bitmap.getHeight() / bitmap.getWidth() * targetWidth);

        // 画像をリサイズ
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);

        // ASCII文字セット
        String asciiChars = "@#S%?*+;:,. ";

        // ピクセルごとに輝度を計算して文字にマッピング
        for (int y = 0; y < resizedBitmap.getHeight(); y++) {
            for (int x = 0; x < resizedBitmap.getWidth(); x++) {
                int pixel = resizedBitmap.getPixel(x, y);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                // 輝度値の計算
                int gray = (int) (0.2989 * red + 0.5870 * green + 0.1140 * blue);
                int charIndex = gray * (asciiChars.length() - 1) / 255;

                asciiArt.append(asciiChars.charAt(charIndex));
            }
            asciiArt.append("\n");
        }

        return asciiArt.toString();
    }

    // アスキーアートをファイルに保存
    private void saveAsciiArt(String asciiArt) {
        File dir = getExternalFilesDir(null);
        if (dir != null) {
            File file = new File(dir, System.currentTimeMillis() + "_ascii_art.txt");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(asciiArt.getBytes());
                fos.flush();
                Toast.makeText(this, "ファイルが保存されました: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this, "ファイルの保存に失敗しました", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
