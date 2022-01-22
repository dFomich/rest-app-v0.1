<jsp:useBean id="dish" type="by.training.restaurant.db.entity.Dish"/>
<div class="dish_container">
    <img src="${pageContext.request.contextPath}/img/dish-${dish.id}.jpg"
            class="dish_img"
            alt="dish-${dish.id}.img"
    />
    <div class="dish_text_info">
        <p class="dish_name">${dish.name}</p>
        <%-- <p class="dish_description">&ensp;${fn:substring(dish.description, 0, 60)}...</p> --%>
        <p class="dish_weight">Weight: ${dish.weight} g</p>
        <p class="dish_price">${dish.price}&#8372;</p>
        <%--        <button class="dish_addtocart btn btn-outline-primary">Add to cart</button>--%>
        <form action="${pageContext.request.contextPath}/cart" method="post">
            <input name="id" style="display: none" value="${dish.id}">
            <input value="1" name="count" style="display: none">
            <input type="submit"
                   value="Add to cart"
                   class="dish_addtocart btn btn-outline-primary">
        </form>
    </div>
</div>
</div>