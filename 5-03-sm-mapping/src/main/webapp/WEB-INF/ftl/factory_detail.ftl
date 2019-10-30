<h3>${factory.id}</h3>
<h4>${factory.name}</h4>
<hr/>
<#list factories as fab>

    ${fab.id}--${fab.name}<br/>

</#list>