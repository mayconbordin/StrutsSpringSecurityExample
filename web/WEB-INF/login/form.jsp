<%@taglib uri="/struts-tags" prefix="s"%>

<s:actionerror />

<s:url id="formSubmit" value='/j_spring_security_check' />

<s:form action="%{formSubmit}" method="post">
<div style="margin: 0 auto;">
    <p>
        <s:label key="label.username" />
        <s:textfield name="j_username" />
        <s:fielderror fieldName="j_username" />
    </p>
    <p>
        <s:label key="label.password" />
        <s:password name="j_password" />
        <s:fielderror fieldName="j_password" />
    </p>
    <p>
        <s:checkbox name="_spring_security_remember_me" />Lembrar minha senha
        <s:fielderror fieldName="_spring_security_remember_me" />
    </p>
    <p>
        <s:submit key="label.submit" name="submit" />
        <s:reset key="label.reset" name="reset" />
    </p>
</div>
</s:form>