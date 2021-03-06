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
        <#list users as user>
            <div class="card">
                <span class="item-name">Name: ${user.name}</span>
                <span class="item-info">Role: ${user.role}</span>
            </div>
        </#list>
    </div>
</main>
</body>
</html>