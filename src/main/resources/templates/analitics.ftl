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
    <div class="popularity-diagrams">
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
    </div>
    <div class="price-diagramms">
        <svg version="1.2" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" class="graph"
             aria-labelledby="title" role="img">
            <title id="title">Product price dynamic</title>
            <g class="grid x-grid" id="xGrid">
                <line x1="90" x2="90" y1="5" y2="371"></line>
            </g>
            <g class="grid y-grid" id="yGrid">
                <line x1="90" x2="705" y1="370" y2="370"></line>
            </g>
            <g class="labels x-labels">
                <text x="100" y="400">Jan</text>
                <text x="173" y="400">Feb</text>
                <text x="246" y="400">Mar</text>
                <text x="319" y="400">Apr</text>
                <text x="392" y="400">May</text>
                <text x="465" y="400">Jun</text>
                <text x="538" y="400">Jul</text>
                <text x="611" y="400">Aug</text>
                <text x="684" y="400">Sep</text>
                <text x="757" y="400">Oct</text>
                <text x="840" y="400">Nov</text>
                <text x="913" y="400">Dec</text>
                <text x="900" y="440" class="label-title">Month</text>
            </g>
            <g class="labels y-labels">
                <text x="80" y="15">15</text>
                <text x="80" y="131">10</text>
                <text x="80" y="248">5</text>
                <text x="80" y="373">0</text>
                <text x="50" y="200" class="label-title">Price</text>
            </g>
            <g class="data" data-setname="Our first data set">
                <circle cx="90" cy="192" data-value="7.2" r="4"></circle>
                <circle cx="240" cy="141" data-value="8.1" r="4"></circle>
                <circle cx="388" cy="179" data-value="7.7" r="4"></circle>
                <circle cx="531" cy="200" data-value="6.8" r="4"></circle>
                <circle cx="677" cy="104" data-value="6.7" r="4"></circle>
            </g>
        </svg>
    </div>
</section>
</script>
</body>
</html>