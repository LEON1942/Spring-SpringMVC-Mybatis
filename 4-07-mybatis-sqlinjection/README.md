# Mybatis 防止SQL注入

##1、Mybatis 防 SQL 注入的基础知识
Mybatis使用mapper接收参数有两种形式，一种是通过#{参数}的形式，另一种则是通过${参数}的形式

例如:
   
    <select id="findUserByName" resultType="...User">
        select * from user where name = #{user_name}
    </select>
 
   
    <select id="findUserByName" resultType="...User">
        select * from user where name = ${user_name}
    </select>
 
区别在于，通过#{}参数传入的SQL是经过预编译的，上例相当于生成：

    Preparing: select * from user where name = ?
    Parameters: username (String)
    
而通过${}生成的SQL是实时编译的，上例相当于生成语句:

    Preparing: select * from user where name = user_name
    Parameters:

如果用户传入的 user_name 附带了SQL语句,例 user_name = 'leon;drop table user;'

则通过#{}参数生成的SQL是：

    select * from user where name = 'leon;drop table user;'

此时并不会对表单造成不良的影响，而通过${}生成的SQL是：

    select * from user where name = leon;drop table user;
    
此时，传入的参数将会导致整张user表单的清空，所以如果不是业务上有特殊要求，不推荐使用${}的方式接收参数

为了更好的做到防止SQL注入，尽量使用#{}的方式接收参数，同时应该对参数进行相应的判断处理，从而在源头上杜绝。
