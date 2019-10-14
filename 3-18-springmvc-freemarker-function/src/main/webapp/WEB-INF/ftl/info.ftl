<h1>${title}</h1>
<h3>${user.name}</h3>
<h3>${user.salary?string("¥0.00")}<br/>
<!-- freemarker 三目运算符 -->
${ (user.salary <= 1000)?string("低收入人群", "高收入人群")}
</h3>
<h3>${user.birthday?string("yyyy年MM月dd日")}</h3>
<h3>${user.address!"未登记地址"}</h3>

<#if user.salary <= 1000>
    革命尚未成功，同志仍需努力
<#elseif 1000 < user.salary && user.salary <= 10000>
    小小成功，继续努力
<#else>
    有钱请任性
</#if>

<#-- ??代表属性是否存在，存在则返回true，不存在则返回false -->
<#if user.manage??>
    <h4>manager存在</h4>
<#else>
    <h4>manager不存在</h4>
</#if>
