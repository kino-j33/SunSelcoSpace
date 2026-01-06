# SunSelcoSpace

SunSelcoSpace は、架空の複合施設「サンセルコスペース」を舞台にした施設予約管理Webアプリケーションです。  
Java職業訓練の総復習として、要件定義・設計・DB構築・実装までを一貫して行いました。

---

## 概要

会議室やレンタルスペースの空き状況を確認し、予約を行うことができるWebアプリケーションです。
Servlet / JSP を用いた **MVCモデル** を採用し、保守性の高いコード構成と、実務を意識したデータベース設計を特徴としています。

---

## 主な機能

- **施設一覧・詳細表示**: DBから取得した施設情報を動的に表示
- **予約登録**: 重複予約の防止を考慮した登録ロジック
- **予約管理**: **論理削除（deletedフラグ）** を用いた運用を想定したデータ管理
- **認証・認可**: ログイン状態に応じたアクセス制御
- **デモ環境**: 初期データ投入SQLによる迅速な環境構築

---

## 使用技術

### バックエンド
- **Java 21** (LTS)
- **Jakarta Servlet 6.0 / JSP 3.1**
- **JSTL 3.0** (Jakarta Standard Tag Library)
- **JDBC / JNDI** (コネクションプールによる効率的なDB接続)

### フロントエンド
- HTML / CSS / JavaScript
- Responsive Design (マルチデバイス対応)

### データベース / インフラ
- **PostgreSQL**
- **Apache Tomcat 10.1** (Jakarta EE 10 準拠)

### ツール
- Maven (依存関係管理)
- Git / GitHub
- Eclipse

---

## ディレクトリ構成

```text
SunSelcoSpace
├─ src
│  └─ main
│     ├─ java
│     │  └─ jp/co/sunselcospace
│     │     ├─ servlet
│     │     ├─ service
│     │     ├─ dao
│     │     ├─ entity
│     │     └─ data (DataSourceFactory等)
│     └─ webapp
│        ├─ META-INF
│        │  └─ context.xml.sample (DB接続テンプレート)
│        ├─ WEB-INF
│        │  └─ web.xml
│        ├─ static (css, js, img)
│        └─ jsp
├─ sql
│  ├─ drop.sql
│  ├─ schema.sql
│  └─ initial_data.sql
├─ pom.xml
└─ README.md
```

## データベース設計

### テーブル構成
- `room`（施設情報）
- `account`（アカウント情報）
- `booking`（予約情報）

### 設計のポイント
- **論理削除の採用**: `booking` テーブルには `deleted` フラグを備え、データを物理的に削除せず保持することで、運用時の誤操作対応や整合性維持を両立。
- **コネクションプールの活用**: Tomcatのリソース管理機能（JNDI）を利用し、高負荷時でも安定したDB接続を確保。

---

## 環境構築・実行手順

### 1. データベースの初期化
`/sql` ディレクトリ内のファイルを以下の順序で実行してください。
1. `drop.sql` (既存テーブルの削除)
2. `schema.sql` (テーブル定義の作成)
3. `initial_data.sql` (デモデータの投入)

### 2. DB接続設定 (JNDI)
本プロジェクトはセキュリティとパフォーマンス向上のため、Tomcatのコネクションプールを使用します。

1. `/src/main/webapp/META-INF/` にある `context.xml.sample` をコピーし、同ディレクトリに `context.xml` を作成します。
2. 作成した `context.xml` 内の `password` を、ご自身のローカル環境に合わせて書き換えてください。
   - ※ `context.xml` は機密情報保護のため `.gitignore` によりGit管理から除外しています。

---

## 工夫した点・学んだこと

- **実務に即した接続管理**: `db.properties` による直接接続から、JNDIを用いたコネクションプール管理へ移行。これにより、DB接続のオーバーヘッド削減と設定の分離を実現。
- **セキュリティの意識**: DBパスワードをGitに含めない運用（テンプレートファイルの活用）や、SQLインジェクション対策としての `PreparedStatement` の徹底。
- **MVCモデルの徹底**: 各レイヤー（Servlet/Service/DAO/Entity）の責務を明確にし、ビジネスロジックとUI表示を完全に分離。
- **モダンなJava環境**: Java 21 / Jakarta EE 10 の最新環境を採用し、最新の仕様に準拠した実装。

---

## 注意事項

本アプリケーションは学習目的で作成したものです。機密情報（パスワード等）の取り扱いには十分に注意してください。