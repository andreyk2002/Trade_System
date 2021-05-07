<header>
    <ul class="header-list">
        <#if user??>
            <li class="header-item">
                <#if user.role == 'GUEST'>
                    <a href="/login">login as member</a>
                <#elseif user.role == 'RESEARCHER'>
                    <a href="/research">Analysis tools</a>
                <#else>
                    <a href="/create">Add item</a>
                </#if>
            </li>
            <#if user.role != 'GUEST'>
                <li class="header-item">
                    <a href="/logout">log out</a>
                </li>
            </#if>
        </#if>
        <li class="header-item">
            <form method="post" action="/search">
                <input required type="text" name="searchString" placeholder="enter item name"/>
                <button type="submit">find</button>
            </form>
        </li>
    </ul>
</header>