<#macro select id datas>
 <select type="text" class="form-control" id="${id}" name="${id}" >
  <option>---请选择---</option>
  <#list datas as data>
   <option value="${data}">${data}</option>
  </#list>
 </select>
</#macro>