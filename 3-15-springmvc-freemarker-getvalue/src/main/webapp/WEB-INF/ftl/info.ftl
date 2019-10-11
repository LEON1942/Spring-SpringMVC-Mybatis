<h1>${title}</h1>
<h3>${user.name}</h3>
<h3>${user.salary?string("¥0.00")}</h3>
<h3>${user.birthday?string("yyyy年MM月dd日")}</h3>
<h3>${user.address!"未登记地址"}</h3>