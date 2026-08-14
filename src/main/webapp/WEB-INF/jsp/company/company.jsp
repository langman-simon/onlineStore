<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="company-page">

    <div class="company-hero">
        <p class="eyebrow"><spring:message code="company.eyebrow"/></p>
        <h1><spring:message code="company.title"/></h1>
        <p><spring:message code="company.intro"/></p>
    </div>

    <div class="company-content">
        <article class="company-card">
            <h2><spring:message code="company.activity.title"/></h2>
            <p><spring:message code="company.activity.text"/></p>
        </article>

        <article class="company-card">
            <h2><spring:message code="company.commitment.title"/></h2>
            <p><spring:message code="company.commitment.text"/></p>
        </article>

        <article class="company-card">
            <h2><spring:message code="company.service.title"/></h2>
            <p><spring:message code="company.service.text"/></p>
        </article>
    </div>

</section>
