<#assign ContextPath=springMacroRequestContext.getContextPath() />
<div class="grid-title">
	<div class="pull-left">
		<div class="icon-title"><i class="icon-table"></i></div>
		<span>产品等级</span> 
		<div class="clearfix"></div>
	</div>
	<div class="clearfix"></div>   
</div>
<div class="grid-content overflow">
	<form id="main-content-form" action="${ContextPath}/data/dict/add_product_grade.html" method="post" class="form-horizontal">
	<table id="main-list-table" class="table table-striped">
		<thead>
			<tr>
				<th>产品等级列表</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><input type="text" name="name" required="true" maxlength="32"></td>
				<td class="action-table">
					<button type="submit" class="btn btn-primary">保存</button>
				</td>
			</tr>
			<#list dicts as dict>
			<tr>
				<td>${dict.name!}</td>
				<td class="action-table">
					<a href="javascript:void(0);" data-url="${ContextPath}/data/dict/delete_product_grade.html?id=${dict.id}"
						class="ajax-action-btn danger last" data-confirm="true" data-action="删除"
						data-title="${dict.name!}" title="删除"><i class="icon-remove"></i></a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	</form>
	<div class="clearfix"></div>
</div>
<script>
$('#main-list-table').dataTable({
	bLengthChange: false,
	bFilter: false,
	bInfo: false,
	bPaginate: false
});
$('input,textarea,select', '#main-content-form').jqBootstrapValidation({
	submitSuccess : function($form, event) {
		event.preventDefault();
		event.stopPropagation();
		$form.ajaxSubmit({
			success : function(response) {
				cqlybest.success(null, null, function(){
					$('#dict-main').load('${ContextPath}/data/dict/product_grade.html');
				});
			},
			error : function() {
				cqlybest.error();
			}
		});
	}
});
</script>