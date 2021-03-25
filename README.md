# winas-lesson-android-day4
2021/3/26のAndroid Lesson Day4のサンプルと課題です

## 課題
Day4Homeworkプロジェクトにコードを記述して、以下のようなアプリを作ってください。
![Sample](https://user-images.githubusercontent.com/34995624/101751280-70557c00-3b13-11eb-92c8-40b9e19b3dc9.gif)

**要件**

```
・最初の画面でStartボタンを押したら、idTextViewとpasswordTextViewのテキストを、(1)SharedPreferences、(2)Roomそれぞれに保存すること。
・コンテンツ画面の鍵ボタンをタップしたらAlertDialogにSharedPreferences、Roomの選択メニューを表示させ、メニューをタップしたらそれぞれ保存されたデータをトーストで表示すること
・コンテンツ画面は、最初にRoomに保存されているキャッシュを表示させること
・サーバ通信(SampleAPI.getList)でデータを取得して、コンテンツ画面に表示させること。取得したデータはRoomにも保存して次回のキャッシュとすること。
・RecyclerViewの各アイテムをタップしたら、Contentの`description`をトーストで表示させること。
```
