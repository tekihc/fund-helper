# Fund-Helper

## 文件

第一次使用插件会自动在resource/fund-helper/目录下生成data.csv文件用于存放用户的基金数据，如果自动登记的信息出现问题也可以手动修改。

## 指令

注：以下指令兼容.。和,，

### .基金

* 功能：查询指定基金详情
* 格式：.基金 <基金编号>
* 返回示例
  ![image](https://github.com/NoErla/fund-helper/blob/master/src/main/resources/image/基金详情.png)
### .添加自选

* 功能：记录自选基金
* 格式：.添加自选 <基金编号1>,<基金编号2>

### .我的基金

* 功能：查询登记的基金情况（周一至周五14：40会自动推送基金信息给好友）
* 格式：.我的基金
* 返回示例
  ![image](https://github.com/NoErla/fund-helper/blob/master/src/main/resources/image/我的基金.png)
### .删除自选

* 功能：删除登记的所有记录
* 格式：.删除自选

### .help

* 功能：查询所有命令详情
* 格式：.help

## 交流

### bug反馈

请说明正在使用的mirai版本、本插件版本。

### pr

欢迎
