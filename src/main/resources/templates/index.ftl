<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="styles/reset.css">
    <link rel="stylesheet" href="styles/style.css">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<#include 'fragments/header.ftl'>
<main>
    <div class="items">
        <#list products as product>
            <div class="card">
                <#if user??>
                <#if user.role == 'ORGANIZATION'>
                    <a href="/edit?itemId=${product.id}">Edit</a>
                </#if>
                </#if>
                <span class="item-name">Name: ${product.name}</span>
                <span class="item-info">Price: ${product.price}USD</span>
                <span class="item-info">Article: ${product.article}</span>
                <a class="buy-link" href="/buy?itemId=${product.id}">Buy</a>
            </div>
        </#list>

    </div>
</main>
</body>
</html>