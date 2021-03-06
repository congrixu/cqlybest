<#assign TopNav=[{
	'name':'工作台',
	'url':'/home.do'
},'divider',{
	'name':'马尔代夫',
	'url':'/maldives.do'
},'divider',{
	'name':'客户关系',
	'url':'/crm.do'
},'divider',{
	'name':'系统管理',
	'url':'/system.do'
}] />
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a href="#" class="brand">
				<small>${Title}</small>
			</a><!--/.brand-->
			<ul class="nav ace-nav pull-right">
				<li class="light-blue user-profile">
					<a class="user-menu dropdown-toggle" href="#" data-toggle="dropdown">
						<#assign avatar='${ContextPath}/img/avatar.gif'>
						<#if User.avatar?has_content>
							<#if User.avatar?starts_with('http')>
								<#assign avatar=User.avatar>
							<#else>
								<#assign avatar='${ContextPath}/image/${User.avatar}'>
							</#if>
						</#if>
						<img src="${avatar}" class="nav-user-photo">
						<span id="user_info">
							<small>你好，</small>
							${User.nickname!'未设置昵称'}!
						</span>

						<i class="icon-caret-down"></i>
					</a>
					<ul class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer" id="user_menu">
						<#if TopNav?has_content>
						<#list TopNav as nav>
						<#if nav?is_string && nav=='divider'>
						<li class="divider"></li>
						<#else>
						<li id="${nav.id!}">
							<a href="<#if nav.url?has_content>${ContextPath}${nav.url}<#else>javascript:void(0);</#if>">
								<i class="icon-wrench"></i> ${nav.name!}
							</a>
						</li>
						</#if>
						</#list>
						</#if>
						<li class="divider"></li>
						<li><a href="${ContextPath}/logout.do"><i class="icon-off"></i> 退出</a></li>
					</ul>
				</li>
			</ul><!--/.ace-nav-->
		</div><!--/.container-fluid-->
	</div><!--/.navbar-inner-->
</div>
<div class="container-fluid" id="main-container">