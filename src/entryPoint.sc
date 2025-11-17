require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
# Подключение javascript обработчиков
require: js/getters.js
require: js/reply.js
require: js/actions.js

# Подключение сценарных файлов


patterns:
    $AnyText = $nonEmptyGarbage
    $Continent = (Европа:Европа|Азия:Азия|Африка:Африка|Америка:Америка|Австралия и Океания:Австралия и Океания|Европу:Европа|Азию:Азия|Африку:Африка|Америку:Америка|Австралию:Австралия и Океания|Австралию и Океанию:Австралия и Океания|Австралия:Австралия и Океания|европа:Европа|азия:Азия|африка:Африка|америка:Америка|австралия и океания:Австралия и Океания|австралия:Австралия и Океания|океания:Австралия и Океания|Океания:Австралия и Океания|Австралия и Океания:Австралия и Океания)
    $Country = (поехали:да|приступить:да|ага:да|угу:да|да:да|продолжить:да|погнали:да|Поехали:да|Приступить:да|Ага:да|Угу:да|Да:да|Продолжить:да|Погнали:да|Go:да|Гоу:да|гоу:да|го:да|go:да|начнем:да|Начнем:да|начнём:да|Начнём:да)

theme: /

    state: Start
        # При запуске приложения с кнопки прилетит сообщение /start.
        q!: $regex</start>
        # При запуске приложения с голоса прилетит сказанная фраза.
        q!: (запусти | открой | вруби | включи | активируй | давай поиграем в | сыграть в | навык | запусти навык) ((изучаем страны миссис:Изучаем страны МИСИС)|(saluteapp))
        #q!: Запусти saluteapp
        script:
            addSuggestions(["Запусти «Изучаем страны МИСИС»"], $context);

    state: Help
        q!: помощь
        a: Для старта необходимо выбрать континент. Для подтверждения скажите Да. Далее можно переключаться между карточками со странами.
        go!: /ContinentMain
      
    state: Fallback
        event!: noMatch
        a: Не понимаю. Нужную информацию можно узнать по команде "помощь".
        script:
            addSuggestions(["Помощь"], $context);
        

    state: Continent1
        event!: continent_start_1
        a: Приветствую! Я могу помочь в изучении столиц множества стран мира! Чтобы начать, нужно выбрать континент. После подтверждения выбора откроются карточки с названиями государств. На обороте карточек написаны их столицы. 
        go!: /ContinentMain
              
    state: Continent2
        event!: continent_start_2
        a: Можем начинать?
        go!: /ContinentMain
        
    state: ContinentMain
        script:
            addSuggestions(["Помощь", "Европа"], $context);

        state: ContinentChoose
            q: * $Continent
            script:
                continentChoose({ continent: $parseTree._Continent }, $context);
            go!: /CountryMain

            
    state: Country1
        event!: country_start_1
        #a: Выберем раздел.
        script:
            saveEventData($context, $session);
        go!: /CountryMain

    state: Country2
        event!: country_start_2
        #a: Выберем раздел.
        script:
            saveEventData($context, $session);
        go!: /CountryMain

    
    state: CountryMain
        script:
             #addSuggestions(["Назад", "Первый", "Второй"], $context); - ТУТ БЫЛО ЭТО, ЕСЛИ ВДРУГ ЧТО СЛОМАЕТСЯ
             addSuggestions(["Назад", "Да"], $context);

        state: CountryChoose
            q: * $Country
            script:
                var continent = loadEventDataParam($session, 'continent');
                var params = { continent: continent};
                countryChoose(params, $context);
            go!: /Learn
                

        state: CountryBack
            q!: (Назад|Вернись|Обратно|Back)
            script:
                goBack($parseTree._Country,$context);
    
    # Не работает

    state: Learn
        #a: Начнем. На лицевой стороне карточки написано название страны, а на обороте - её столица. Можно использовать кнопки или голосовое управление, чтобы переключаться между карточками.
        go!: /LearnStart1
    
    state: LearnStart1
        event!: learn_start_1
        a: Начнем. На лицевой стороне карточки написано название страны, а на обороте - её столица. Можно использовать кнопки или голосовое управление, чтобы переключаться между карточками.
        script:
            saveEventData($context, $session);
            addSuggestions(["Перевернуть карточку", "Следующая карточка"], $context);
        go!: /LearnMain

    state: LearnStart2
        event!: learn_start_2
        a: Что делать с карточкой?
        script:
            saveEventData($context, $session);
            var continent = loadEventDataParam($session, 'continent');
            var step = loadEventDataParam($session, 'step');
            var params = { continent: continent, step: step };
            log("AAA", params)
            switch (params) {
                case ({continent: "Европа", step: 0}):
                    addSuggestions(["Перевернуть карточку", "Следующая карточка"], $context);
                case ({continent: "Азия", step: 0}):
                    addSuggestions(["Перевернуть карточку", "Следующая карточка"], $context);
                case ({continent: "Африка", step: 0}):
                    addSuggestions(["Перевернуть карточку", "Следующая карточка"], $context);
                case ({continent: "Америка", step: 0}):
                    addSuggestions(["Перевернуть карточку", "Следующая карточка"], $context);
                case ({continent: "Австралия и Океания", step: 0}):
                    addSuggestions(["Перевернуть карточку", "Следующая карточка"], $context);
                case ({continent: "Европа", step: 46}):
                    addSuggestions(["Перевернуть карточку", "Результат", "Следующая карточка"], $context);
                case ({continent: "Азия", step: 53}):
                    addSuggestions(["Перевернуть карточку", "Результат", "Следующая карточка"], $context);
                case ({continent: "Африка", step: 57}):
                    addSuggestions(["Перевернуть карточку", "Результат", "Следующая карточка"], $context);
                case ({continent: "Америка", step: 35}):
                    addSuggestions(["Перевернуть карточку", "Результат", "Следующая карточка"], $context);
                case ({continent: "Австралия и Океания", step: 14}):
                    addSuggestions(["Перевернуть карточку", "Результат", "Следующая карточка"], $context);
                default:
                    addSuggestions(["Перевернуть карточку", "Следующая карточка", "Предыдущая карточка"], $context);
            }
            $reactions.transition('/LearnMain');
            
    state: LearnMain

        state: AsLearnFlip
            q!: (Перевернуть| Узнать столицу| Столица | Flip) [карточку]
            script:
                addSuggestions(["Перевернуть карточку"], $context);
                var params = loadEventData($session);
                learnFlip(params, $context);

        state: UiLearnFlip
            event!: learn_flip
            a: Готово!

        state: LearnPrev
            q!: (Предыдущая [страна|карточка]|Вернись|Обратно|Prev)
            script:
                var params = loadEventData($session);
                learnPrev(params, $context);
    
        state: LearnNext
            q!: (Следующая [страна|карточка]|Вперед|Дальше|Next)
            script:
                var params = loadEventData($session);
                learnNext(params, $context);
    
        state: LearnBack
            q!: (Назад|Вернись|Обратно|Back)
            script:
                var params = loadEventData($session);
                goBackCard(params, $context);

        state: Result
            q!: (Результат)
            script:
                var params = loadEventData($session);
                result(params, $context);
            