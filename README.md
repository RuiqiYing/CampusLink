
# CampusLink

CampusLink 是一款为大学生和教职工提供交流与协作的平台。该应用集成了多种功能，包括聊天、管理联系人、查看学生信息等。应用使用 Kotlin (KT) 编写，优化了在 Android 设备上的顺畅体验。

## 功能

- **聊天模块**：
  - 支持与学生和教职工的实时消息沟通。
  - 消息保存在本地数据库中，确保消息持久化存储。
  - 提供发送和接收消息、显示消息的多种功能。

- **社区模块**：
  - 为学生提供一个分享更新、活动和信息的平台。
  - 用户可以发布公告，阅读他人的发布内容。
  - 包括社区相关数据的数据库管理。

- **联系人管理**：
  - 用户可以管理联系人，查看联系人详情，添加新联系人到数据库。
  - 支持联系人项界面，包括消息和个人资料。

- **主界面**：
  - 提供用户信息的集中展示，包括个人资料、密码管理和账户设置。
  - 方便用户进行登录和注册。

- **用户注册与登录**：
  - 提供安全的登录与注册界面。
  - 管理用户凭证、密码和会话数据。

## 目录结构

```
CAMPUSLINK
│
├── chat
│   ├── add_to_database.kt
│   ├── chat_interface.kt
│   ├── chat.kt
│   └── ... (其他聊天相关文件)
│
├── Community
│   ├── add_database.kt
│   ├── CoDatabaseHelper.kt
│   ├── communityActivity.kt
│   └── ... (其他社区相关文件)
│
├── contact
│   ├── add_contact.kt
│   ├── con_class.kt
│   ├── conAdapter.kt
│   └── ... (其他联系人相关文件)
│
├── main_interface
│   ├── add_to_person_database.kt
│   ├── change_password.kt
│   ├── change_student_informationActivity.kt
│   └── ... (其他主界面文件)
│
├── sign_in_up
│   ├── loginActivity.kt
│   ├── RegisterActivity.kt
│   ├── chat_information.kt
│   └── ... (其他注册登录相关文件)
│
└── README.md
```

## 安装

1. 克隆该仓库：
   ```bash
   git clone https://github.com/RuiqiYing/CampusLink.git
   ```

2. 在 Android Studio 中打开该项目。

3. 在 Android 设备或模拟器中运行应用。

## 先决条件

- Kotlin (KT)
- Android Studio
- Android SDK

## 贡献

欢迎社区贡献，可以通过 Fork 仓库、提交 Pull Request 或者报告问题。

