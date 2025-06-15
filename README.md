# 要件定義書 - TeamReportAI（仮）

---

## 1. アプリの目的

このアプリは、チームの日報業務を効率化し、  
・投稿者：簡単に書ける  
・管理者：すぐに把握できる  
・チーム全体：AIによって精度・効率を高める  
ことを目的とする。

また、AI（GPT）による校正・要約機能、Teams・メール通知機能を統合し、**実用性の高い業務支援ツール**として完成させる。

---

## 2. 想定ユーザー

| ロール | 権限 | 主な操作 |
|--------|------|----------|
| 一般ユーザー | 自分の投稿・コメント・Q&Aのみ | 日報投稿・閲覧・AI校正・要約・GPT Q&A |
| 管理者 | 全体操作 | 全日報閲覧／承認／コメント／CSV出力／通知実行（Teams／メール） |

---

## 3. 機能一覧

- [ ] ログイン（JWT認証）
- [ ] 日報投稿／編集／削除
- [ ] コメント機能（管理者向け・絵文字対応）
- [ ] 日報一覧・フィルター（ユーザー別・期間・ステータス）
- [ ] AI校正（誤字／表現改善）※OpenAI API使用
- [ ] AI要約（3行要約生成）
- [ ] GPT Q&A（業務系質問への回答）
- [ ] Teams通知（提出時）
- [ ] メール通知（未提出リマインド：自前SMTP）
- [ ] CSV出力（日報データ）
- [ ] 管理画面（未提出者確認・ユーザー管理）
- [ ] ダークモード切替（任意）
- [ ] GitHub ActionsによるCI/CD
- [ ] VPSへの自動デプロイ（systemd＋Nginx）

---

## 4. 画面一覧

| パス | 機能概要 |
|------|----------|
| `/login` | JWTログイン画面 |
| `/dashboard` | 日報一覧・検索・CSV出力 |
| `/report/new` | 日報投稿・AI校正・要約ボタン |
| `/report/:id` | 日報詳細・コメント表示・追加 |
| `/qa` | GPT Q&AチャットUI |
| `/admin` | ユーザー管理／未提出確認／メール送信操作など |

---

## 5. データ構成（ER図相当）

###  User
- user_id（PK）
- username（ログインID）
- password（ハッシュ）
- role（admin/user）

### Report
- report_id（PK）
- user_id（FK）
- title
- content
- status（enum：draft / submitted / approved）
- submitted_at

### Comment
- comment_id（PK）
- report_id（FK）
- user_id（FK）
- content（絵文字含む）
- created_at
- updated_at

### QuestionLog
- question_id（PK）
- user_id（FK）
- type（proofread / summary / general）
- input_text
- output_text
- timestamp

---

## 6. 使用技術スタック

| 分類 | 技術 |
|------|------|
| バックエンド | Java 17, Spring Boot, Spring Security (JWT), JPA |
| フロント | Vue.js 3, Bootstrap 5 |
| DB | PostgreSQL |
| 通知 | Microsoft Teams Webhook, JavaMailSender |
| AI連携 | OpenAI GPT-4 API（Chat Completion） |
| サーバー | Ubuntu VPS（ConoHa等） |
| メールサーバー | Postfix + Dovecot（TLS/SSL） |
| デプロイ | GitHub Actions + Nginx + systemd |
| 文字コード | utf8mb4（絵文字対応） |

---

## 7. 想定運用

- VPSに常駐。Spring Bootアプリをsystemdで管理
- GitHubへのPushで自動ビルド＆デプロイ
- OpenAIのAPIキーは`.env` or GitHub Secretsで安全に管理
- コメント／日報は全てDB保存し、履歴と可視性を確保
