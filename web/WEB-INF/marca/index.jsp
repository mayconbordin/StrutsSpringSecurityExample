<%@taglib uri="http://displaytag.sf.net" prefix="display" %> 
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>

<!-- set the namespace of the view -->
<s:set name="namespace">/marca</s:set>

<h1>Index of Marca</h1>

<div id="messages">
    <s:actionerror theme="jquery" />
    <s:actionmessage theme="jquery" />
</div>

<div id="tableOptions">
    <s:url id="addURL" action="add" namespace="%{#namespace}" />
    <s:submit value="Add" onclick="window.location.href = '%{addURL}';" />

    <!-- Search bar -->
    <s:include value="../helpers/searchBar.jsp" />
</div>

<!-- Get the options column name -->
<s:set name="options"><s:text name="table.header.options" /></s:set>
<s:set name="pagesize"><s:text name="table.pagesize" /></s:set>

<!-- Start: Display Tag Table -->
<display:table id="marca" name="paginatedList" defaultsort="1" pagesize="${pagesize}" export="false" requestURI="/marca/index.action">
    <!-- Set the current user id -->
    <s:set name="id">${marca.id}</s:set>

    <!-- Create the edit url -->
    <s:url id="editURL" action="edit" namespace="%{#namespace}">
        <s:param name="marcaId" value="#id"></s:param>
    </s:url>

    <!-- Create the delete url -->
    <s:url id="deleteURL" action="delete" namespace="%{#namespace}">
        <s:param name="marcaId" value="#id"></s:param>
    </s:url>

    <!-- Start: COLUMNS -->
    <display:column titleKey="" >
        <input type="checkbox" name="select" value="${marca.id}"/>
    </display:column>

    <display:column property="id" value="label.id" href="${editURL}" sortable="true" sortName="id" />
    
    <display:column property="nome" value="label.nome" sortable="true" />

    
    <display:column title="${options}">

        <!-- Edit button -->
        <s:submit value="Edit" onclick="window.location.href = '%{editURL}';" />

        <!-- Start: Remove dialog -->
        <sj:dialog
            id="remove%{#id}"
            buttons="{
                    'Yes':function() {
                        $.post('%{deleteURL}', function(data) {
                            $('#remove%{#id}').dialog('close');
                            $('#successRemove').dialog('open');
                        });
                     },
                    'No':function() { $(this).dialog('close'); }
                    }"
            autoOpen="false"
            modal="true"
            title="Remove Marca"
        >
        Do you want to remove marca <s:property value="#id"/>?
        </sj:dialog>
        <!-- End: Remove dialog -->

        <!-- Button to open remove dialog -->
        <sj:submit
            openDialog="remove%{#id}"
            value="Remove"
            button="false"
        />

    </display:column>
    <!-- End: COLUMNS -->
    <display:setProperty name="paging.banner.placement" value="bottom" />
</display:table>
<!-- End: Display Tag Table -->

<sj:dialog
    id="successRemove"
    buttons="{
            'OK':function() { $(this).dialog('close'); }
            }"
    autoOpen="false"
    modal="true"
    title="Marca removed!"
>
Marca successful removed!
</sj:dialog>
