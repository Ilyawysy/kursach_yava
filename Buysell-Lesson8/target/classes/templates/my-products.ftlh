<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Мои билеты</h1>
    <hr>
    <#list user.roles as role>
        <#if role == "ROLE_ADMIN">
            <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
               data-toggle="collapse" href="#collapseExample" role="button"
               aria-expanded="true" aria-controls="collapseExample">
                Добавить новый билет
            </a>
            <div class="collapse mb-3" id="collapseExample" style="">
                <form action="/product/create" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="productName">Название</label>
                        <input type="text" required class="form-control"
                               id="productName" name="title">
                    </div>
                    <div class="form-group">
                        <label for="price">Цена билета</label>
                        <div class="input-group mb-3">
                            <input type="number" required class="form-control" id="price" name="price">
                            <div class="input-group-append">
                                <span class="input-group-text">₽</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="searchDepCity">Город</label>
                        <select id="searchDepCity" required name="depCity" class="form-control">
                            <option value="">Город не выбран</option>

                            <option value="Москва">Москва</option>
                            <option value="Красноярск">Красноярск</option>
                            <option value="Казань">Казань</option>

                        </select>
                    </div>
                    <div class="form-group">
                        <label for="searchArrCity">Город</label>
                        <select id="searchArrCity" required name="arrCity" class="form-control">
                            <option value="">Город не выбран</option>

                            <option value="Москва">Москва</option>
                            <option value="Красноярск">Красноярск</option>
                            <option value="Казань">Казань</option>

                        </select>
                    </div>
                    <div class="form-group">
                        <label for="description">Описание</label>
                        <textarea required class="form-control" id="description" name="description"></textarea>
                    </div>
                    <div class="form-group">
                        <div class="custom-file">
                            <input required type="file" name="file1" id="file1">
                            <label class="custom-file-label" for="file1">Фотография</label>
                        </div>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                    <button type="submit" style="width: 100%" class="btn btn-dark">Добавить</button>
                </form>
            </div>
            <div class="card-columns">
                <#list products as product>
                    <a href="/product/${product.id}" class="product-link">
                        <div class="card text-white bg-dark my-3 product">
                            <div class="card-top">
                                <form action="/product/delete/${product.id}" method="post">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-danger m-3">
                                        Удалить
                                    </button>
                                </form>
                            </div>
                            <img src="/images/${product.previewImageId}"
                                 class="card-img-top">
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
                <#else>
                    <h1 style="text-align: center">Пока билетов нет!</h1>
                </#list>
            </div>
        </#if>
    </#list>
</@t.template>