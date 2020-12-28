<div class="ibox-title">
    <div class="builder-tabs builder-form-tabs">
        <ul class="nav nav-tabs">
            <li<#if active == "system:setting:system"> class="active" </#if>><a href="${basePath}/system/setting/system">系统设置</a></li>
            <li<#if active == "system:setting:sms"> class="active" </#if>><a href="${basePath}/system/setting/sms">短信配置</a></li>
            <li<#if active == "system:sms:list"> class="active" </#if>><a href="${basePath}/system/sms/list">短信模板</a></li>
            <li<#if active == "system:setting:qiniu"> class="active" </#if>><a href="${basePath}/system/setting/qiniu">七牛云上传配置</a></li>
            <li<#if active == "system:setting:jpush"> class="active" </#if>><a href="${basePath}/system/setting/jpush">极光推送配置</a></li>
        </ul>
    </div>
</div>