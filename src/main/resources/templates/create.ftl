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
<main>
    <#include 'fragments/header.ftl'>
    <div class="register">
        <form class="form-login" action="/addItem" method="post">
            <input required class="input-control" type="text" name="name" placeholder="item name"/>
            <input class="input-control" type="hidden" name="organizationId" value="2"/>
            <input required class="input-control" type="text" name="article" placeholder="item article"/>
            <input required class="input-control" type="number" name="price" min="0" placeholder="enter item price">
            <select required class="categories-select" name="categoryId">
                <#list categories as category>
                    <option name="category" value="${category.id}">${category.name}</option>
                </#list>
            </select>
            <button class="login-button" type="submit">Create</button>
        </form>
    </div>
</main>
</body>
</html>