<doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<#include 'fragments/header.ftl'>
<div class="register">
    <form class="form-login" action="/edit" method="post">
        <input type="hidden" name="itemId" value="${item.id}">
        <label>
            Item name
            <input required class="input-control" type="text" name="name" value="${item.name}"/>
        </label>
        <label>
            Item price
            <input required class="input-control" type="number" name="price" min="0" value="${item.price}">
        </label>
        <label>
            Item category
            <select required class="categories-select" name="categoryId">
                <#list categories as category>
                    <option name="category" value="${category.id}">${category.name}</option>
                </#list>
            </select>
        </label>
        <button class="login-button" type="submit">Save</button>
    </form>
</div>
</body>
</html>