@SLF4J

Feature: Тест Яндекс маркета
  @example
 Scenario Outline: Проверка по категории "<servicesMarket>-<sub-services>-<manufacturer>"
    Given перейти на сайт "https://yandex.ru/"
    When выбрать и перейти на сервис "Маркет"
    Then открыть категории товаров
    Then выбрать категорию товаров "<servicesMarket>"
    Then выбрать подкатегорию "<sub-services>" и перейти по ссылке
    Then установить цену от "<priceFrom>" до "<priceTo>"
    Then выбрать производителя "<manufacturer>"
    Then установить отображение элементов на странице не более 12
    Then найти первое предложение из представленных
    Then венести первое предложение из представленных в поисковую строку
    Then найти первое предложение из вновь представленных
    Then сравнить товары
    And закрыть браузер
    Examples:
     | servicesMarket| sub-services | priceFrom | priceTo | manufacturer |
     | Дача и сад | Снегоуборщики | 5000 | 50000 | Caiman |
     | Дача и сад | Снегоуборщики | 12000 | 49800 | Habert |
     | Мебель | Компьютерные кресла | 10000 | 30000 | SIGNAL |
     | Мебель | Компьютерные кресла | 5000 | 17000 | Zombie |
