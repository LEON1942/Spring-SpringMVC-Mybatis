#FreeMarker取值

##${属性名} - 取值，可对属性进行计算
     ${user.name}
##${属性名!默认值} - 使用默认值
    ${user.address!"未登记地址"}
##${属性名?string} - 格式化输出
    ${user.salary?string("¥0.00")}
    ${user.birthday?string("yyyy年MM月dd日")}

