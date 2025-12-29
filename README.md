# SunSelcoSpace

SunSelcoSpace は、架空の複合施設「サンセルコスペース」を舞台にした  
施設予約管理Webアプリケーションです。  
Java職業訓練で学習した内容の総復習として、設計から実装までを行いました。

---

## 概要

会議室やレンタルスペースなどの施設情報を一覧表示し、  
ユーザーが予約を行える Web アプリケーションです。

Servlet / JSP を用いた基本的な MVC 構成を意識し、  
データベースと連携した Web アプリケーションの基礎を実装しています。

---

## 主な機能

- 施設一覧表示
- 施設詳細表示
- 予約登録機能
- 予約情報管理（論理削除）
- 初期データ投入によるデモ環境構築

---

## 使用技術

### バックエンド
- Java 21
- Jakarta Servlet 6.0
- JSP / JSTL（Jakarta JSTL 3.0）
- JDBC

### フロントエンド
- HTML / CSS
- JSP

### データベース
- PostgreSQL

### 開発環境
- Eclipse
- Apache Tomcat 10
- Maven
- Git / GitHub

---

## ディレクトリ構成

```text
SunSelcoSpace
├─ src
│  └─ main
│     ├─ java
│     │  └─ jp/co/sunselcospace
│     │     ├─ servlet
│     │     ├─ dao
│     │     ├─ entity
│     │     └─ constant
│     ├─ resources
│     └─ webapp
│        ├─ WEB-INF
│        │  └─ web.xml
│        └─ jsp
├─ sql
│ ├─ drop.sql
│ ├─ schema.sql
│ └─ initial_data.sql
├─ pom.xml
└─ README.md
```

---

## データベース設計

### テーブル構成
- room（施設情報）
- account（アカウント情報）
- booking（予約情報）

### 設計方針
- booking テーブルは論理削除（deleted フラグ）を採用
- アカウント削除後も予約履歴を保持できる設計
- 外部キー制約は account にのみ設定

---

## データベース初期化手順

以下の順序で SQL を実行してください。

1. `drop.sql`
2. `schema.sql`
3. `initial_data.sql`

---

## DB接続設定（db.properties）

本プロジェクトでは、DB接続情報を  
`src/main/resources/db.properties` にて管理しています。

※ db.properties は `.gitignore` により Git 管理対象外としています。
※ 各自のローカル環境に合わせて、以下の内容で作成してください。

```properties
db.driver=org.postgresql.Driver
db.url=jdbc:postgresql://localhost:5432/sunselcospace
db.user=ユーザー名
db.password=パスワード
```

---

## 工夫した点・学んだこと

- Servlet / JSP による MVC 構成を意識した実装
- 論理削除を用いたデータ管理の設計
- Java コードの評価に影響を与えない範囲での DB 設計改善
- Maven を用いた依存関係管理による環境差異の抑制
- GitHub での公開を意識した、再現性のあるプロジェクト構成

---

## 今後の改善点

- 入力チェックの強化
- UI / UX の改善

---

## 注意事項

本アプリケーションは学習目的で作成したものです。  