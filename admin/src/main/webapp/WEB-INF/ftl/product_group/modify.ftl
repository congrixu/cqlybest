<#assign ContextPath=springMacroRequestContext.getContextPath() />
<style>
.group-cnd {
	margin: 0 0 10px;
}
.group-cnd span {
	margin: 0 10px;
}
.group-cnd-plus {
	margin-left: 5px;
}
.group-cnd-plus > span {
	padding: 2px 5px 1px 2px;
}
.group-cnd-remove {
}
.group-cnd-remove > span{
    margin: 0 0 0 5px;
    padding: 3px 2px 2px 4px;
}
</style>
<div>
	<div class="clearfix"></div>
	<div class="grid">
		<div class="grid-title">
			<div class="pull-left">
				<div class="icon-title"><i class="icon-edit"></i></div>
				<span>修改产品聚合</span> 
				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>   
		</div>
		<!-- novalidate -->
		<form id="main-content-form" action="${ContextPath}/product_group/modify.html" method="post" class="form-horizontal">
			<input type="hidden" name="id" value="${(group.id)!}">
			<div class="grid-content">
				<ul class="nav nav-tabs">
					<li class="active"><a href="javascript:void(0);" data-toggle="tab" data-target="#product-group-tab">聚合条件</a></li>
					<li><a href="javascript:void(0);" data-toggle="tab" data-target="#product-group-filter-tab">过滤条件</a></li>
				</ul>
				<#assign groupCndNames=['推荐月份', '适合人群', '交通方式', '产品类型', '产品等级', '关键词/标签', '出发城市', '目的地']>
				<#macro selectedDictRender dict values>
					<#assign items = values?split(",")>
					<#list dict as item>
						<#if items?seq_contains('${item.id}')>
						<span class="s_green">${item.name!}</span>
						</#if>
					</#list>
				</#macro>
				<div class="tab-content">
					<div class="tab-pane active" id="product-group-tab">
						<div class="control-group">
							<label class="control-label">聚合名称：</label>
							<div class="controls">
								<input type="text" class="span input" name="name" value="${(group.name)!}" required="true" maxlength="16"> 
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">聚合条件：</label>
							<div id="group-cnds" class="controls">
								<#list group.groupItems as item>
								<div class="group-cnd">
									<span class="help-inline"><strong>按${groupCndNames[item.groupType]}</strong><a href="javascript:void(0);" class="group-cnd-remove"><span class="label label-important"><i class="icon-remove"></i></span></a>：</span>
									<#if item.groupType==0>
										<#list item.groupValue?split(",")  as value>
										<span class="s_green">${value}月</span>
										</#list>
									</#if>
									<#if item.groupType==1>
										<#assign crowds={'1':'个人旅行','2':'团体旅行'}>
										<#list item.groupValue?split(",")  as value>
										<span class="s_green">${crowds[value]}</span>
										</#list>
									</#if>
									<#if item.groupType==2>
										<@selectedDictRender traffics item.groupValue />
									</#if>
									<#if item.groupType==3>
										<@selectedDictRender types item.groupValue />
									</#if>
									<#if item.groupType==4>
										<@selectedDictRender grades item.groupValue />
									</#if>
									<#if item.groupType==5>
										<@selectedDictRender keywords item.groupValue />
									</#if>
									<#if item.groupType==6>
										<@selectedDictRender departureCities item.groupValue />
									</#if>
									<#if item.groupType==7>
										<@selectedDictRender destinations item.groupValue />
									</#if>
									<input type="hidden" name="groupTypes" value="${item.groupType}">
									<input type="hidden" name="groupValues" value="${item.groupValue!}">
								</div>
								</#list>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="product-group-filter-tab">
						<div class="control-group">
							<label class="control-label">过滤条件：</label>
							<div id="filter-cnds" class="controls">
								<#list group.filterItems as item>
								<div class="group-cnd">
									<span class="help-inline"><strong>按${groupCndNames[item.filterType]}</strong><a href="javascript:void(0);" class="group-cnd-remove"><span class="label label-important"><i class="icon-remove"></i></span></a>：</span>
									<#if item.filterType==0>
										<#list item.filterValue?split(",")  as value>
										<span class="s_green">${value}月</span>
										</#list>
									</#if>
									<#if item.filterType==1>
										<#assign crowds={'1':'个人旅行','2':'团体旅行'}>
										<#list item.filterValue?split(",")  as value>
										<span class="s_green">${crowds[value]}</span>
										</#list>
									</#if>
									<#if item.filterType==2>
										<@selectedDictRender traffics item.filterValue />
									</#if>
									<#if item.filterType==3>
										<@selectedDictRender types item.filterValue />
									</#if>
									<#if item.filterType==4>
										<@selectedDictRender grades item.filterValue />
									</#if>
									<#if item.filterType==5>
										<@selectedDictRender keywords item.filterValue />
									</#if>
									<#if item.filterType==6>
										<@selectedDictRender departureCities item.filterValue />
									</#if>
									<#if item.filterType==7>
										<@selectedDictRender destinations item.filterValue />
									</#if>
									<input type="hidden" name="filterTypes" value="${item.filterType}">
									<input type="hidden" name="filterValues" value="${item.filterValue!}">
								</div>
								</#list>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center">
					<button class="btn btn-primary" type="submit">保存</button>
				</div>
			</div>
		</form>
		<div class="grid-content form-horizontal">
			<div class="control-group">
				<label class="control-label"><strong>备选条件：</strong></label>
			</div>
			<hr style="margin:0 0 10px;">
			<div class="control-group">
				<label class="control-label">推荐月份<a class="group-cnd-plus" href="javascript:void(0);" data-type="0"><span class="s_green"><i class="icon-plus"></i></span></a>：</label>
				<div class="controls">
					<label class="checkbox inline"><input value="1" type="checkbox"> 1月</label>
					<label class="checkbox inline"><input value="2" type="checkbox"> 2月</label>
					<label class="checkbox inline"><input value="3" type="checkbox"> 3月</label>
					<label class="checkbox inline"><input value="4" type="checkbox"> 4月</label>
					<label class="checkbox inline"><input value="5" type="checkbox"> 5月</label>
					<label class="checkbox inline"><input value="6" type="checkbox"> 6月</label>
					<label class="checkbox inline"><input value="7" type="checkbox"> 7月</label>
					<label class="checkbox inline"><input value="8" type="checkbox"> 8月</label>
					<label class="checkbox inline"><input value="9" type="checkbox"> 9月</label>
					<label class="checkbox inline"><input value="10" type="checkbox"> 10月</label>
					<label class="checkbox inline"><input value="11" type="checkbox"> 11月</label>
					<label class="checkbox inline"><input value="12" type="checkbox"> 12月</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">适合人群<a class="group-cnd-plus" href="javascript:void(0);" data-type="1"><span class="s_green"><i class="icon-plus"></i></span></a>：</label>
				<div class="controls">
					<label class="checkbox inline"><input value="1" type="checkbox"> 个人旅行</label>
					<label class="checkbox inline"><input value="2" type="checkbox"> 团体旅行</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">交通方式<a class="group-cnd-plus" href="javascript:void(0);" data-type="2"><span class="s_green"><i class="icon-plus"></i></span></a>：</label>
				<div class="controls">
					<#list traffics as dict>
					<label class="checkbox inline"><input value="${dict.id!}" type="checkbox"> ${dict.name!}</label>
					</#list>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">产品类型<a class="group-cnd-plus" href="javascript:void(0);" data-type="3"><span class="s_green"><i class="icon-plus"></i></span></a>：</label>
				<div class="controls">
					<#list types as dict>
					<label class="checkbox inline"><input value="${dict.id!}" type="checkbox"> ${dict.name!}</label>
					</#list>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">产品等级<a class="group-cnd-plus" href="javascript:void(0);" data-type="4"><span class="s_green"><i class="icon-plus"></i></span></a>：</label>
				<div class="controls">
					<#list grades as dict>
					<label class="checkbox inline"><input value="${dict.id!}" type="checkbox"> ${dict.name!}</label>
					</#list>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">关键词/标签<a class="group-cnd-plus" href="javascript:void(0);" data-type="5"><span class="s_green"><i class="icon-plus"></i></span></a>：</label>
				<div class="controls">
					<input type="text" class="input" id="product-keyword">
					<div class="help-block"></div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">出发城市<a class="group-cnd-plus" href="javascript:void(0);" data-type="6"><span class="s_green"><i class="icon-plus"></i></span></a>：</label>
				<div class="controls">
					<input type="text" class="input" id="product-departure-city">
					<div class="help-block"></div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">目的地<a class="group-cnd-plus" href="javascript:void(0);" data-type="7"><span class="s_green"><i class="icon-plus"></i></span></a>：</label>
				<div class="controls">
					<input type="text" class="input" id="product-destination">
					<div class="help-block"></div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<script>
$('#product-keyword').cqlybestTag({
	hiddenTagListName: 'keywordIds',
	typeahead: true,
	typeaheadAjaxSource: '/data/dict.html?action=dict&type=keyword',
	typeaheadAjaxPolling: true,
	AjaxPush: '/data/dict/add_keyword.html'
});
$('#product-departure-city').cqlybestTag({
	hiddenTagListName: 'departureCityIds',
	typeahead: true,
	typeaheadAjaxSource: '/data/dict.html?action=dict&type=departure-city',
	typeaheadAjaxPolling: true,
	AjaxPush: '/data/dict/add_departure_city.html'
});
$('#product-destination').cqlybestTag({
	hiddenTagListName: 'destIds',
	typeahead: true,
	typeaheadAjaxSource: '/data/dict.html?action=dict&type=destination',
	typeaheadAjaxPolling: true
});

var groupCndNames  =  ['推荐月份', '适合人群', '交通方式', '产品类型', '产品等级', '关键词/标签', '出发城市', '目的地'];
$('#group-cnds .group-cnd-remove').click(function(){
	$(this).parents('.group-cnd').remove();
});
var generateCnd = function(t, name, objs, type, value) {
	if ($('#' + t + '-cnds .group-cnd').length >= 5) {
		bootbox.alert('<div class="alert alert-error">当前最多只允许5个条件</div>', '确定');
		return;
	}
	var html =  ['<div class="group-cnd"><span class="help-inline"><strong>按' + name + '</strong>'];
	html.push('<a href="javascript:void(0);" class="group-cnd-remove"><span class="label label-important">');
	html.push('<i class="icon-remove"></i></span></a>：</span>');
	$.each(objs, function(i, obj){
		html.push('<span class="s_green">' + obj + '</span>');
	});
	html.push('<input type="hidden" name="' + t + 'Types" value="' + type + '">');
	html.push('<input type="hidden" name="' + t + 'Values" value="' + value + '"></div>');
	var dom = $(html.join(''));
	$('.group-cnd-remove', dom).click(function(){
		$(this).parents('.group-cnd').remove();
	});	
	$('#' + t + '-cnds').append(dom);
};
var checkboxCnds = ['.group-cnd-plus[data-type=0]'];
checkboxCnds.push('.group-cnd-plus[data-type=1]');
checkboxCnds.push('.group-cnd-plus[data-type=2]');
checkboxCnds.push('.group-cnd-plus[data-type=3]');
checkboxCnds.push('.group-cnd-plus[data-type=4]');
$(checkboxCnds.join(',')).click(function(){
	var type = $(this).attr('data-type');
	var value = [];
	var names =  [];
	var objs = $('input:checked', $(this).parents('.control-group'));
	if (objs.length == 0) {
		bootbox.alert('<div class="alert alert-error">至少勾选一个值</div>', '确定');
		return;
	}
	$.each(objs, function(i, obj){
		value.push($(obj).val());
		names.push($.trim($(obj).parent().text().trim()));
	});
	if ($('#group-cnds').is(":visible"))  {
		generateCnd('group', groupCndNames[type], names, type, value.join(','));
	} else {
		generateCnd('filter', groupCndNames[type], names, type, value.join(','));
	}
});
var tagCnds = ['.group-cnd-plus[data-type=5]'];
tagCnds.push('.group-cnd-plus[data-type=6]');
tagCnds.push('.group-cnd-plus[data-type=7]');
$(tagCnds.join(',')).click(function(){
	var type = $(this).attr('data-type');
	var parent = $(this).parents('.control-group');
	var value = $('input:hidden', parent).val();
	var names = [];
	$('.myTag > span', parent).each(function(i, obj){
		names.push($(obj).text());
	});
	if (names.length==0) {
		bootbox.alert('<div class="alert alert-error">至少输入/选择一个值</div>', '确定');
		return;
	}
	if ($('#group-cnds').is(":visible"))  {
		generateCnd('group', groupCndNames[type], names, type, value);
	} else {
		generateCnd('filter', groupCndNames[type], names, type, value);
	}
});
$('input,textarea,select', '#main-content-form').not(':hidden').jqBootstrapValidation({
	submitSuccess : cqlybest.ajaxSubmit
});
</script>