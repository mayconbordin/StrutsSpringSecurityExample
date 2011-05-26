<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>

<div id="messages">
<s:actionerror />
</div>

<s:push value="produto">
	<s:hidden name="id" />
	
    <p>
        <s:label key="label.nome" />
        <s:textfield name="nome" />
        <s:fielderror fieldName="nome" />
    </p>
    <p>
        <s:label key="label.marca" />
        <sj:autocompleter id="marca" name="marca.id" list="%{marcaList}" listValue="nome" listKey="id" selectBox="true" />
        <s:fielderror fieldName="marca" />
    </p>

	<p>
	    <s:hidden name="save" value="true" />
	    <s:submit key="label.save" name="" />
	    <s:submit key="form.cancel" onclick="window.location.href = './index.action';return false;" />
	</p>
</s:push>
