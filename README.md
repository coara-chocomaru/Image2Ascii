
# 画像をASCIIアートに変換するAndroid用アプリ(image to ASCII)

このAndroidアプリは画像をASCIIアートに変換する事が出来ます。

## 機能

- デバイスのストレージから画像を選択。
- 選択した画像をASCIIアートに変換。
- 生成されたASCIIアートを `.txt` ファイルとして内部ストレージ(/sdcard/Android/data/com.image2ascii/files/内に保存されます)
- アプリ内で直接ASCIIアートを表示。(端末によって表示結果レイアウトが崩れるが生成されたtxtに問題はない)

## 使用方法

1. **権限を付与**:
   - 初回起動時にアプリはストレージへのアクセス許可を求めますので
　　　権限を許可してください。

2. **画像を選択**:
   - 「画像を選択」ボタンをタップしてストレージを開きます。
   - ストレージから画像ファイルを選択します。

3. **ASCIIアートに変換**:
   - 画像を選択すると、「ASCIIに変換」ボタンが有効になります。
   - ボタンをタップして変換を開始します。
   - ASCIIアートがアプリ内に表示されます。

4. **ASCIIアートを保存**:
   - ASCIIアートは自動的にアプリの/sdcard/Android/data/com.image2ascii/files/内に `.txt` ファイルとして保存されます。
   - 保存されたファイルの場所は通知メッセージとして表示されます。

## 使用ライブラリ

このアプリは以下のライブラリを使用しています：

- **AndroidX ライブラリ**:
  - `androidx.appcompat:appcompat:1.7.0`
  - `androidx.core:core:1.12.0`
  - `androidx.constraintlayout:constraintlayout:2.2.0`
  - `androidx.activity:activity-ktx:1.8.0`

このアプリの参考元ソースは下記です
https://tool-taro.com/blog/java/230/

## 権限

アプリには以下の権限が必要です：

- **ストレージ権限**:
  - `READ_EXTERNAL_STORAGE`: デバイスから画像ファイルを読み取るため。
  - `WRITE_EXTERNAL_STORAGE`: 生成されたASCIIアートをテキストファイルとして保存するため。

## 対応画像フォーマット

- `.jpg`
- `.png`
- `.bmp`

##　※注意事項と補足
端末によっては変換後の表示文字の
レイアウトが崩れますが
ストレージ内に保存されたtxtをPCなどで開けば正常に生成されてるのが確認出来ます。

また、このアプリは更新予定がありませんので
自由にカスタマイズしてお使い下さい。
