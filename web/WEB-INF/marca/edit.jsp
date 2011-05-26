<%@taglib uri="/struts-tags" prefix="s"%>

<h1>Edit Marca ${marcaId}</h1>

<div>
	<s:if test="marca != null">
		<s:form action="edit" namespace="/marca">
		    <s:include value="form.jsp" />
		</s:form>
	</s:if>
	<s:else>
		<s:text name="error.notFound" />
	</s:else>
</div>
