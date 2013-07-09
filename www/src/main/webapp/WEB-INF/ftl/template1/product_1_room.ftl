<div id="maldives-island-room－tab" class="maldives-island-section">
	<div class="title">
		<h4>房型介绍</h4>
	</div>
	<#if product.maldives?has_content>
	<#list product.maldives as maldives>
	<#assign room=rooms[maldives_index]>
	<div class="row-fluid product-room">
		<div class="span2">
			<div class="day well well-small">${maldives.name!}</div>
		</div>
		<div class="span10">
			<table class="table table-condensed">
				<colgroup>
					<col width="70" />
					<col width="" />
				</colgroup>
				<tbody>
					<tr>
						<th colspan="2" class="name">${room.zhName!}${room.enName!}</th>
					</tr>
					<#if room.requirements?has_content>
					<tr>
						<th>入住要求：</th>
						<td>${room.requirements}</td>
					</tr>
					</#if>
					<#if room.roomFacility?has_content>
					<tr>
						<th>房间设施：</th>
						<td>${room.roomFacility}</td>
					</tr>
					</#if>
					<#if room.description?has_content>
					<tr>
						<th>详细信息：</th>
						<td>${room.description}</td>
					</tr>
					</#if>
				</tbody>
			</table>
			<#if room.pictures?has_content>
			<div class="room-gallery" data-toggle="modal-gallery" data-target="#product-gallery">
				<#list room.pictures as image>
				<#assign imageUrl='${ContextPath}/image/${image.id}.${image.imageType}'>
				<a data-gallery="gallery" href="${imageUrl}" title="${image.title!}"><img src="${imageUrl}?width=100&height=100"></a>
			</#list>
			</div>
			</#if>
		</div>
	</div>
	</#list>
	</#if>
</div>