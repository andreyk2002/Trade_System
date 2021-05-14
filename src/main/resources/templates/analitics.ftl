<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="styles/reset.css">
    <link rel="stylesheet" href="styles/style.css">
    <title>Document</title>
</head>
<body>
<#include 'fragments/header.ftl'>
<div class="research" style="padding-bottom: 30px">
    <div class="register">
        <form class="form-login" action="/searchCategories" method="post">
            <h2 class="search-header">Search orders by category</h2>
            <label>Earliest date:
                <input required class="input-control" type="datetime-local" name="minDate" placeholder="Earliest date"/>
            </label>
            <label>Latest date:
                <input required class="input-control" type="datetime-local" name="maxDate" placeholder="Latest date">
            </label>
            <select required class="categories-select" name="categoryId">
                <#list categories as category>
                    <option name="category" value="${category.id}">${category.name}</option>
                </#list>
            </select>
            <button class="login-button" type="submit">Search</button>
        </form>
    </div>
    <div class="register">
        <form class="form-login" action="/searchUsers" method="post">
            <h2 class="search-header">Search orders by client name</h2>
            <input required class="input-control" type="text" name="productName" placeholder="ordered product name">
            <button class="login-button" type="submit">Search</button>
        </form>
    </div>
</div>
<section class="statistic">
    <h1>Current statistic by items</h1>
    <#list orders as orderName, orderPoints>
        <div class="diagram">
            <svg width="500" height="225">
                <line x1="0" y1="200" x2="200" y2="200" style="stroke:black;stroke-width:4"></line>
                <line x1="0" y1="200" x2="0" y2="0" style="stroke:black;stroke-width:4"></line>
                <polyline points="${orderPoints}" style="fill:none;stroke:black;stroke-width:3"></polyline>
                <text x="200" y="15" fill="black">product: ${orderName}</text>
                <text x="0" y="-10" fill="black" transform="rotate(90)">popularity</text>
                <text x="100" y="220">date</text>
            </svg>
        </div>
    </#list>
</section>
</script>
</body>
</html>