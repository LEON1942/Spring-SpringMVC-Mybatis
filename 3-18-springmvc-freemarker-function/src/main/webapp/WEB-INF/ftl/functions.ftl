<#include "/include/header.ftl">

<h4>${author?upper_case}</h4>
<h4>${author?lower_case}</h4>
<h4>${author?cap_first}</h4>
<h4>${author?index_of("O")}</h4>
<h4>${author?length}</h4>

<hr/>
<#list users as u>
    ...
    ${u_index+1} - ${u.name} - ${u.age}
    ${u.salary?round}-${u.salary?floor}-${u.salary?ceiling}<br/>
</#list>
共${users?size}
${users?first}
${users?last}

<hr/>
<#list users?sort_by("age") as u>
    ...
    ${u_index+1} - ${u.name} - ${u.age}
    ${u.salary?round}-${u.salary?floor}-${u.salary?ceiling}
    <br/>
</#list>

<#include "/include/footer.ftl">