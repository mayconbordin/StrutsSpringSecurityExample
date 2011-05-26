<%@taglib uri="/struts-tags" prefix="s" %>

<div id="searchBar" style="float:right;">
<s:form action="index" namespace="%{#namespace}" method="get">
    <s:text name="search.in" />
    <s:select name="searchParams.field" list="fields" listKey="key" listValue="name" />
    <s:text name="search.for" />
    <s:textfield name="searchParams.value" />
    <s:hidden name="search" value="true" />
    <s:submit key="search.submit" name="" />
    <s:submit key="search.reset" onclick="window.location.href = './index.action';return false;" />
</s:form>
</div>
