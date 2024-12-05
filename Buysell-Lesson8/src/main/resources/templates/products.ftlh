<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Поиск билетов</h1><hr>
    <form method="get" action="/">
        <div class="form-row">
            <div class="form-group col-md-5">
                <label for="depCity">Город вылета</label>
                <select id="depCity" name="depCity" class="form-control">
                    <option value="">Город не выбран</option>
                    <option value="Москва" <#if depCity?? && depCity == "Москва">selected</#if>>Москва</option>
                    <option value="Красноярск" <#if depCity?? && depCity == "Красноярск">selected</#if>>Красноярск</option>
                    <option value="Казань" <#if depCity?? && depCity == "Казань">selected</#if>>Казань</option>
                </select>
            </div>

            <div class="form-group col-md-5">
                <label for="arrCity">Город прилета</label>
                <select id="arrCity" name="arrCity" class="form-control">
                    <option value="">Город не выбран</option>
                    <option value="Москва" <#if arrCity?? && arrCity == "Москва">selected</#if>>Москва</option>
                    <option value="Красноярск" <#if arrCity?? && arrCity == "Красноярск">selected</#if>>Красноярск</option>
                    <option value="Казань" <#if arrCity?? && arrCity == "Казань">selected</#if>>Казань</option>
                </select>
            </div>

            <div class="form-group col-md-5">
                <label for="minPrice">Минимальная цена</label>
                <input type="number" name="minPrice" <#if minPrice??>value="${minPrice}"</#if> class="form-control"
                       id="minPrice" placeholder="Введите минимальную цену">
            </div>

            <div class="form-group col-md-5">
                <label for="maxPrice">Максимальная цена</label>
                <input type="number" name="maxPrice" <#if maxPrice??>value="${maxPrice}"</#if> class="form-control"
                       id="maxPrice" placeholder="Введите максимальную цену">
            </div>
            <button style="width: 100%" type="submit" class="btn btn-dark">Поиск</button>
        </div>
    </form>
    <hr>

    <!-- Grid system with Bootstrap for displaying 3 items per row -->
    <div class="row">
        <#list products as product>
            <div class="col-md-4">
                <a href="/product/${product.id}" class="product-link">
                    <div class="card text-white bg-dark my-3 product">
                        <img src="/images/${product.previewImageId}" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title">${product.title}</h5>
                            <p class="card-text">${product.price} ₽</p>
                        </div>
                        <div class="card-footer">
                            <#if product.depCity?? && product.arrCity??>
                                ${product.depCity} - ${product.arrCity}
                            <#else>
                                Город не указан
                            </#if>
                        </div>
                    </div>
                </a>
            </div>
        <#else>
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
    </div>
</@t.template>
