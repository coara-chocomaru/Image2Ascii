# Image2Ascii

このAndroidアプリは、任意の画像をASCIIアートに変換できます

## 機能

- デバイスのストレージから画像を選択し、画像をASCIIアートに変換
- 生成されたASCIIアートを `.txt` ファイルとして  
  内部ストレージ(`/sdcard/Android/data/com.coara.image2ascii/files/`)に保存
- アプリ内で直接ASCIIアートを表示  
  ※端末の画面密度によってはレイアウトが崩れます

## 使用方法

1. **権限を付与**:
    - 初回起動時にアプリはストレージへのアクセス許可を要求しますので、権限を許可してください。

2. **画像を選択**:
    - ｢<kbd>画像を選択</kbd>｣ボタンをタップしてストレージを開きます。
    - ストレージから画像ファイルを選択します。

3. **ASCIIアートに変換**:
    - 画像を選択すると、｢<kbd>ASCIIに変換</kbd>｣ボタンが有効になります。
    - ボタンをタップして変換を開始します。
    - ASCIIアートがアプリ内に表示されます。

4. **ASCIIアートを保存**:
    - ファイルに保存をする場合、ASCIIアートは `/sdcard/Android/data/com.coara.image2ascii/files/` に `.txt` ファイルとして保存されます。
    - 保存場所はボタンの下に表示されます。

## 権限

アプリには以下の権限が必要です：

- **ストレージ権限**:
  - `READ_EXTERNAL_STORAGE`: デバイスから画像ファイルを読み取るため。
  - `WRITE_EXTERNAL_STORAGE`: 生成されたASCIIアートをテキストファイルとして保存するため。
  - `READ_MEDIA_IMAGES`: Android 13 以降に対応  
    ※ギャラリーの画像のみ対応

## 対応画像フォーマット

- `.jpg`
- `.png`
- `.bmp`

## 注意事項
画像全体が表示されない場合、縦横にスクロールしてください。

## 参考

- [**画像→AA**](https://tool-taro.com/blog/java/230/)

## ライセンス
Apache License, Version 2.0
###
使用ライブリについては下記のNOTICEをご覧ください。
#####
[NOTICE](./NOTICE.md)
- Copyright 2025 coara-chocomaru

