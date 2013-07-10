<div id="maldives-island-room－tab" class="maldives-island-section">
	<div class="title">
		<h4>房型介绍</h4>
		<hr>
	</div>
	<#if island.rooms?has_content>
	<#list island.rooms as room>
	<table class="table table-condensed">
		<colgroup>
			<col width="70" />
			<col width="" />
		</colgroup>
		<tbody>
			<tr style="background:#5bb75b;">
				<th colspan="2">${room.zhName!}${room.enName!} <#if room.num?has_content><span class="label label-success">${room.num}间</span></#if></th>
			</tr>
			<tr>
				<th>入住要求：</th>
				<td>${room.requirements!}</td>
			</tr>
			<tr>
				<th>房间设施：</th>
				<td>${room.roomFacility!}</td>
			</tr>
			<tr>
				<th>详细信息：</th>
				<td>${room.description!}</td>
			</tr>
		</tbody>
	</table>
	<#if room.pictures?has_content>
	<div class="room-gallery"><#list room.pictures as image><#assign imageUrl='${ContextPath}/image/${image.id}.${image.imageType}'><a data-gallery="gallery" href="${imageUrl}" title="${image.title!}"><img src="${imageUrl}?width=100&height=100"></a></#list></div>
	</#if>
	</#list>
	</#if>
</div>