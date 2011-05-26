<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>

<div id="messages">
<s:actionerror />
</div>

<s:push value="marca">
	<s:hidden name="id" />
	
    <p>
        <s:label key="label.nome" />
        <s:textfield name="nome" />
        <s:fielderror fieldName="nome" />
    </p>

	<p>
	    <s:hidden name="save" value="true" />
	    <s:submit key="label.save" name="" />
	    <s:submit key="form.cancel" onclick="window.location.href = './index.action';return false;" />
	</p>
</s:push>
