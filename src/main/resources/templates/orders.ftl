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
        <#list orders as order>
            <div class="card">
                <span class="item-name">Name: ${order.productName}</span>
                <span class="item-info">Price: ${order.price}USD</span>
                <span class="item-info">Order Date: ${order.date?substring(0, 10)}</span>
            </div>
        </#list>
    </div>
</main>
</body>
</html>