<%@taglib uri="/struts-tags" prefix="s"%>

<h1>Edit Produto ${produtoId}</h1>

<div>
	<s:if test="produto != null">
		<s:form action="edit" namespace="/produto">
		    <s:include value="form.jsp" />
		</s:form>
	</s:if>
	<s:else>
		<s:text name="error.notFound" />
	</s:else>
</div>
