# practice5

spring boot：透過https://start.spring.io/ 建立專案

spring data JPA：移除原先的 JPAConfig，最低限度配置連線資訊及使用方言即可、repository 取代 dao、分頁排序功能有內建的 Page 和 Sort 可運用、service/controller/.html 調整

PO 導入：persistent object、ORM 框架中資料庫 table 對應的 java 物件

.yml：可替代.properties，注意換行及空格

配置類：Initializer 自動配置(簡化 web 配置/根配置/請求註冊)、不需 AppConfig 及 WebConfig 中關於 Template 和 ViewResolver 的配置、Converter 拉出來建立成一個類、靜態資源配置改寫在.yml 中

cache .yml 配置供應商，於 service 類加上相關註解@Cache...、啟動類加上啟動註解

task 啟動類加上啟動註解，在方法上加上@Scheduled
