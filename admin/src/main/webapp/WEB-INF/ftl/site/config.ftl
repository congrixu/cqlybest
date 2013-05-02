<#assign ContextPath=springMacroRequestContext.getContextPath() />
<div>
	<div class="clearfix"></div>
	<div class="grid">
		<div class="grid-title">
			<div class="pull-left">
				<div class="icon-title"><i class="icon-cogs"></i></div>
				<span>网站配置</span> 
				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>   
		</div>
		<!-- novalidate -->
		<form id="main-content-form" action="${ContextPath}/site/config.html" method="post" class="form-horizontal">
			<div class="grid-content">
				<div class="control-group">
					<label class="control-label">网站名称：</label>
					<div class="controls">
						<input type="text" class="span input" name="name" value="${(site.name)!}" required="true" maxlength="256"> 
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">关键字（词）：</label>
					<div class="controls">
						<input type="text" class="span input" name="metaKeyword" value="${(site.metaKeyword)!}" maxlength="256"> 
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">网站描述：</label>
					<div class="controls">
						<input type="text" class="span input" name="metaDescription" value="${(site.metaDescription)!}" maxlength="1024"> 
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">ICP备案号：</label>
					<div class="controls">
						<input type="text" class="span input" name="icp" value="${(site.icp)!}" maxlength="32"> 
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">网站底部版权文字：</label>
					<div class="controls">
						<input type="text" class="span input" name="copyright" value="${(site.copyright)!}" maxlength="1024"> 
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">统计代码：</label>
					<div class="controls">
						<textarea rows="3" name="statisticalCode" class="span input same-height-1" maxlength="1024">${(site.statisticalCode)!}</textarea>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">图片服务器：</label>
					<div class="controls">
						<textarea rows="3" name="imageServer" class="span input same-height-1" maxlength="256">${(site.imageServer)!}</textarea>
					</div>
				</div>
				<div class="text-center">
					<button class="btn btn-primary" type="submit">保存</button>
				</div>
				<div class="clearfix"></div>
			</div>
		</form>
	</div>
</div>
<script>
$('input,textarea,select', '#main-content-form').jqBootstrapValidation({
	submitSuccess : function($form, event) {
		event.preventDefault();
		event.stopPropagation();
		$form.ajaxSubmit({
			success : function(response){
				alert('保存成功');
			},
			error : function() {
				alert('保存失败');
			}
		});
	}
});
</script>