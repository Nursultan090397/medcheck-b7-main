insert into roles(id, role_name)
values (1, 'ADMIN'),
       (2, 'USER');

insert into users(id, user_email, user_first_name, user_last_name, user_password, user_phone_number, role_id)
values (1, 'admin@gmail.com', 'Nursultan', 'Osorov',
        '$2a$12$0Kq466TKTGOiwqFxMvXhjuJJUVmEIYgIEgDrRzuluj0kqrc9QUQf.', '0700000062', 1),
       (2, 'syimyk@gmail.com', 'Syimyk', 'Bayaliev', '$2a$12$0GpUA5ZMhi21OQjMN/MDlO5IavMCTiCC/jT8GbiGXuXrixahwLcJi',
        '+996700000063', 2),
       (3, 'bakai@gmail.com', 'Bakai', 'Koichubaev', '$2a$12$scyH/UzsVBgt2zQ6st4FWeNagO8F4yy3RWs8jVxsKA71jRKgdGUHa',
        '+996555555555', 2),
       (4, 'nurkyz@gmail.com', 'Nurkyz', 'Kasymova', '$2a$12$VV3lra52ZSyb8EqgId7naOIqWu1dS76c2JNVrlvQ94H4G3eSw9Qoy',
        '+996777777777', 2),
       (5, 'sapar@gmail.com', 'Sapar', 'Degenbaev', '$2a$12$y9fRudn61UaKbUaiC/q7CeQKdkXPMwc16ZTkR.yQCX/INDDagEDRK',
        '+996505444444', 2),
       (6, 'sultan@gmail.com', 'Sultan', 'Ibraev', '$2a$12$44DK0XBc5RK.xjlk8CPyTuGWMSfJ8j4xDIS23vrWPZtiHKIVFWQ5O',
        '+996222222222', 2);

insert into applications(id, date, first_name, phone_number, status)
values (1, '2022-05-06', 'Бакай', '+996709032787', FALSE),
       (2, '2020-01-01', 'Нурсултан', '+996555656565', FALSE);

insert into clinic_services(id, clinic_service_name)
values (1, 'АЛЛЕРГОЛОГИЯ'),
       (2, 'ВАКЦИНАЦИЯ'),
       (3, 'ГИНЕКОЛОГИЯ'),
       (4, 'КАРДИОЛОГИЯ'),
       (5, 'НЕЙРОХИРУРГИЯ'),
       (6, 'ОРТОПЕДИЯ'),
       (7, 'ОФТАЛЬМОЛОГИЯ'),
       (8, 'ПСИХОТЕРОПИЯ'),
       (9, 'РЕВМОТОЛОГИЯ'),
       (10, 'УРОЛОГИЯ'),
       (11, 'ЭНДОКРИНОЛОГИЯ'),
       (12, 'АНЕСТЕЗИОЛОГИЯ'),
       (13, 'ГАСТРОЭНТЕРОЛОГИЯ'),
       (14, 'ДЕРМАТОЛОГИЯ'),
       (15, 'НЕВРОЛОГИЯ'),
       (16, 'ОНКОЛОГИЯ'),
       (17, 'ОТОРИНОЛАРИНГОЛОГИЯ'),
       (18, 'ПРОКТОЛОГИЯ'),
       (19, 'ПУЛЬМОНОЛОГИЯ'),
       (20, 'ТЕРАПИЯ'),
       (21, 'ФЛЕБОЛОГИЯ'),
       (22, 'ФИЗИОТЕРАПИЯ');

insert into experts(id, expert_first_name, expert_image, expert_information, expert_last_name, expert_position,
                    expert_status, clinic_service_id)
values (1, 'Салтанат', 'https://medik.kg/doctor/omusheva/',
        'ОБРАЗОВАНИЕ
2000 г. — «Аллерголог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек
тест: https://medik.kg/doctor/omusheva/', 'Омушева', 'Главный врач',true, 1),
       (2, 'Эркинбек', 'https://medik.kg/media/doctor/Эсекеев_Эркинбек_Базарбаевич.jpg',
        'О ВРАЧЕ
     Лечение всех видов варикозных болезней с применением новейших технологий и методик:
     - Эндовязальная лазерная облитерация (ЭВЛО)
     - Склеротерапия
     - Минифлебэктомия
     Также, проводит лечение венозных тромбозов, трофических язв
          ОБРАЗОВАНИЕ
     2012 г. — «Сосудистый хирург (ангиолог)», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек
     тест: https://medik.kg/doctor/esekeev/', 'Эсекеев', 'Главный врач', true, 2),
       (3, 'Жамал', 'https://medik.kg/media/doctor/Screenshot_20191009_134513.jpg',
        'Образование
    1998 г. — «Гинеколог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач высшей категории)
    тест: https://medik.kg/doctor/omuralieva/', 'Омуралиева', 'Врач', true, 3),
       (4, 'Назира ', 'https://medik.kg/media/doctor/Кудайбергенова_Назира_Төрөбаевна.jpg',
        'О ВРАЧЕ
    В ее работу входит лечение таких заболеваний, как🔻
    ⠀
    ✔ врожденные или приобретенные пороки сердца;
    ✔ ишемическая болезнь — нехватка кислорода сердечной мышцы;
    ✔ кардиомиопатия, эндокардиты, миокардиты;
    ✔ аритмические состояния, когда сбиваются ритмы и частота сокращений сердечной мышцы;
    ✔ аневризма аорты;
    ✔ инфаркты, тромбозы, тромбофлебит;
    ✔ атеросклероз;
    ✔ стенокардия;
    ✔ сердечная недостаточность;
    ✔ гипертония
     

      ОБРАЗОВАНИЕ
    1994 г. — «Кардиолог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач высшей категории)
    тест: https://medik.kg/doctor/kudaibergenova/', 'Кудайбергенова', 'Врач', true, 4),

       (5, 'Талант', 'https://medik.kg/media/doctor/Жапаров_Талант_Сагындыкович.jpg',
        'ОБРАЗОВАНИЕ
    1990 г. — «Нейрохирург», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач высшей категории)
    ЛЕЧЕНИЕ БОЛЕЗНЕЙ
    Инсульт
    Абсцесс головного мозга
    Атаксия
    Атаксия Фридрейха
    Атеросклероз сосудов головного мозга
    Отек мозга
    тест: https://medik.kg/doctor/zhaparov/', 'Жапаров', 'Главный врач', true, 5),
       (6, 'Марсель', 'https://medik.kg/media/doctor/Асхатов_Марсель_Асхатович.jpg',
        'ОБРЗОВАНИЕ
2016 г. — Диплом, «Ортопед», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек
Курсы повышения квалификации
2018 г. — «Эндопротезирование», Южная Корея
2018 г. — «Мануальная терапия», Южная Корея
тест: https://medik.kg/doctor/askhatov/', 'Асхатов', 'Главный врач', true, 6),
       (7, 'Айгуль', 'https://medik.kg/media/doctor/Ибраимова_Айгуль.jpg',
        'ОБРАЗОВАНИЕ
1992 г. — «Офтальмолог (окулист)», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач высшей категории)
тест: https://medik.kg/doctor/ibraimova/', 'Ибраимова', 'Врач', true, 7),
       (8, 'Тынара', 'https://medik.kg/media/doctor/Токсонбаева_Тынара_Нурманбетовна.jpeg',
        'ОБРАЗОВАНИЕ
1982 г. — «Психотерапевт», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач высшей категории)
тест: https://medik.kg/doctor/toksonbaeva/', 'Токсонбаева', 'Главный врач', true, 8),
       (9, 'Артем', 'https://medik.kg/media/doctor/Елистратов.jpg',
        ' 
ОБРАЗОВАНИЕ
2000 г. — Ординатура, «Ревматолог», НЦКиТ им. М.Миррахимова
1997 г. — Диплом, «Ревматолог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек

КУРСЫ ПОВЫШЕНИЯ КВАЛИФИКАЦИИ
2019 г. — «I Международный конгресс «Человек и Лекарство – Кыргызстан»», г. Бишкек
2018 г. — «Ревматология», НОЦ при НЦКТ, Бишкек
2017 г. — «Научно-практическая конференция «Инновационные методы терапии в клинической практике врача ревматолога»», г. Алматы


ЛЕЧЕНИЕ БОЛЕЗНЕЙ
Ревматоидный артрит
Системная красная волчанка
Склеродермия
Полимиозит
Дерматомиозит
Синдром Шегрена
Инфекционный (септический) артрит
Подагра
Остеопороз
Болезнь Педжета
Амилоидоз
Ревматизм
Полиартрит
Полиартроз
Волчанка
тест: https://medik.kg/doctor/elistratov/', 'Елистратов', 'Врач', true, 9),
       (10, 'Омурбек', 'https://medik.kg/media/doctor/catalog_66461e3fc984969d770f917faba045f5.jpg',
        'О ВРАЧЕ
Приём, обследование и лечение пациентов с урологической и андрологической патологией ' ||
        '(эректильная дисфункция‚ преждевременная эякуляция‚ ЗППП‚ доброкачественная гиперплазия предстательной железы (ДГПЖ),' ||
        ' неспецифические воспалительные заболевания органов мочеполовой системы - простатит‚ уретрит и др). Специализации: 1997г. -' ||
        ' «Актуальные вопросы урологии» 2005 г. - «Избранные вопросы урологии» 2010 г. - «Амбулаторная урология» В 2004 году - участник' ||
        ' международного семинара под эгидой Фонда народонаселения ООН в КР «Урология и общая практика в целях улучшения сексуального и репродуктивного здоровья для всех» В 2009 году – участник международного семинара в г. Чолпон- Ата, посвященному вопросам лечения аденомы простаты и мочекаменной болезни.
ОБРАЗОВАНИЕ
1994 г. — «Уролог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек
Места приема
ОЦСМ Октябрьского района №6 — Уролог
МЦ On Clinic — Уролог

ЛЕЧЕНИЕ БОЛЕЗНЕЙ
Почечная недостаточность
Гломерулонефрит
Нефротический синдром
Нефрит
Цистит
Пиелонефрит
Уретрит
Простатит
Киста почки
Рак почки
Рак мочевого пузыря
Баланит
Баланопостит
Варикоцеле
Везикулит
Гидроцеле
Задержка мочи (ишурия)
Фимоз и парафимоз
Хламидиоз
Сифилис
Гидронефроз
тест: https://medik.kg/doctor/musakeev/', 'Мусакеев', 'Главный врач', false, 10),
       (11, 'Оксана', 'https://medik.kg/media/doctor/Screenshot_20191028_141828.jpg',
        'ОБРАЗОВАНИЕ
2013 г. — «Эндокринолог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек
Курсы повышения квалификации
2019 г. — «Сертификат о повышении квалификации по специальности «Детская эндокринология №6»», ESPE (Европейская Ассоциация детских эндокринологов). Бишкек
2019 г. — «Сертификат об окончании курса «Клиническая нутрициология и диетология»», ESPE (Европейская Ассоциация детских эндокринологов). Бишкек
2018 г. — «Сертификат о повышении квалификации по специальности «Детская эндокринология №5»», ESPE (Европейская Ассоциация детских эндокринологов). Бишкек
2016 г. — «Сертификат об окончании курса «Ультразвуковая диагностика (УЗИ)»», КГМА им И.К.Ахунбаева. Бишкек
2014 г. — «Сертификат о прохождении учебного семинара «Рациональная эндокринологическая практика»», ФГБУ «Эндокринологический Научный Центр» МЗ РФ. Республиканское Общественное Объединение — Ассоциация врачей –эндокринологов Казахстана. Алматы
2013 г. — «Сертификат участия в конференции по диабетологии и прослушала лекции по вопросам инсулинотерапии при сахарном диабете 1-го и 2-го типов.», ФГБУ «эндокринологический научный Центр» МЗ РФ, Диабетическая и Эндокринологическая Ассоциация Кыргызстана, КГМИПиПК. Бишкек
2013 г. — «Сертификат о прохождении обучения «Актуальные вопросы тиреодологии»», КГМИПиПК
тест: https://medik.kg/doctor/terekhova/', 'Терехова', 'Главный врач', false, 11),
       (12, 'Айнура', 'https://medik.kg/media/doctor/turatbekova.jpg',
        'О враче
      ОПЫТ РАБОТЫ
2018 г. по настоящее время - Городская детская клиническая больница скорой медицинской помощи, анестезиолог-реаниматолог отделения реанимации и токсикологии.
2018 г. по настоящее время - «Учебно-лечебно-научный медицинский центр» КГМА, анестезиолог-реаниматолог.
     Образование
2016 г. — «Анестезиолог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек
тест: https://medik.kg/doctor/turatbekova/', 'Туратбекова', 'Врач', false, 12),
       (13, 'Нуржан', 'https://medik.kg/media/doctor/madanova.jpg',
        'ОБРАЗОВАНИЕ
1992 г. — «Гастроэнторолог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач высшей категории)
тест: https://medik.kg/doctor/madanova/', 'Маданова', 'Врач', false, 13),
       (14, 'Лола', 'https://medik.kg/media/doctor/catalog_a4d56793b643bb9986961c561c5d3a55.jpg',
        'О враче
 Профессиональные навыки:Диагностика и лечение заболеваний кожи: псориаза,экземы и др.заболеваний
Диагностика и лечение дерматологических заболеваний (акне, себорейный дерматит, псориаз, экзема,демадекоз,вульгарные угри,аллергический дерматит и др.);
Удаление доброкачественных кожных образований радиоволновым методом (бородавок, папиллом, кондилом), лечение паховой эритразмы (грибок)
Лечение пиодермии,лечение розового лишая – Жибера
 
2009г.-Последняя специализация по дерматиологии. Прошла специализацию “ Современной технологии Востановительной медицины в трихологии” г.Москва 2009г.
Образование
1994 г. — «Дерматолог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек
Лечение болезней
Эритема
Панникулит
Опоясывающий герпес
Контагиозный моллюск
Акне
Псориаз
Алопеция
тест: https://medik.kg/doctor/alimbaeva/', 'Алимбаева', 'Врач', false, 14),
       (15, 'Мария', 'https://medik.kg/media/doctor/Rekaeva-Mariya-Ivanovna.jpg',
        'О враче
    Невролог или невропатолог — это врач, который диагностирует и лечит заболевания нервной системы. Неврологи подразделяются на детских и взрослых, это разные специализации, так как детская нервная система сильно отличается от взрослой и требует более мягких методов лечения.
    Врачи неврологи МЦ «UNIMED Clinic» оказывают квалифицированную медицинскую помощь, используя современные методы профилактики, диагностики, лечения и реабилитации.
    Невролог: что лечит у взрослых?
    Существует огромное количество заболеваний неврологического характера, вот несколько из них:
    головные боли и мигрени;
    эпилепсия, судороги;
    боли в спине и шее;
    травмы позвоночника и их последствия;
    инсульт и его последствия;
    болезнь Паркинсона;
    болезнь Альцгеймера;
    бессонница;
    повышенное артериальное давление;
    боли, связанные с поражением нерва;
    рассеянный склероз.
     
    Образование
    2004 г. — «Невролог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач высшей категории)
    тест: https://medik.kg/doctor/rekaeva/', 'Рекаева', 'Главный врач', false, 15),
       (16, 'Надира', 'https://medik.kg/media/doctor/Алимова_Надира_Елдошевна.jpg',
        'Образование
    2000 г. — Диплом, «Онколог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач высшей категории)
    Курсы повышения квалификации
    2020 г. — «Лечение злокачественных опухолей»
    тест: https://medik.kg/doctor/alimova/', 'Алимова', 'Главный врач', true, 16),
       (17, 'Айнагул', 'https://medik.kg/media/doctor/Сатыбалдиева_Айнагуль_Жапаровна.jpg',
        'Образование
    1984 г. — Диплом, «Отоларинголог (лор)», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач высшей категории)
    Курсы повышения квалификации
    2020 г. — «Профилактика, диагностика и лечение коронавир.инфекций»
    тест: https://medik.kg/doctor/satybaldieva/', 'Садыбалдиева', 'Врач', true, 17),
       (18, 'Юлия', 'https://medik.kg/media/doctor/jWfCRQ3pxEy1Ek2JcHK16ZoU3uqBMic5ryaCMwgTvnPhC3DYy1Fq.jpg',
        'О враче
Проведение функциональных исследований при заболеваниях прямой кишки; инструментальные методы коагуляции геморроидальных узлов; наложение латексных колец на геморроидальные узлы; выполнение малоинвазивных операций; иссечение анальных трещин, свищей прямой кишки, удаление анальных полипов; перианальная косметология.
        Образование
2004 г. — «Проктолог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек
тест: https://medik.kg/doctor/pogorelova/', 'Погорелова', 'Главный врач', false, 18),
       (19, 'Батыр', 'https://medik.kg/media/doctor/osmonov.jpg',
        'НАУЧНАЯ ДЕЯТЕЛЬНОСТЬ
2012-2014 гг. - Младший научный сотрудник в ежегодной высокогорной экспедиции по оказанию практической помощи жителям высокогорья, совместно с Цюрихским Университеским Госпиталем
2014-2018 гг. - Заместитель начальника экспедиции в ежегодной высокогорной экспедиции по оказанию практической помощи жителям высокогорья, совместно с Цюрихским Университеским Госпиталем
2013 г. - Ассистент профессора в исследовательском проекте ХОБЛ и высокогорье – Цюрихский Университетский Госпиталь, Давос, Швейцария
2015 г. - Научный сотрудник в проекте «Заболевания, обусловленные высокогорьем»
       Образование
2014 г. — Ординатура, «Пульмонолог», НЦКиТ им. М.Миррахимова
2011 г. — Диплом, «Лечебное дело», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек
тест: https://medik.kg/doctor/osmonov/', 'Осмонов', 'Врач', false, 19),
       (20, 'Сырга', 'https://medik.kg/media/doctor/Жумалиева_Сырга_Акматбековна_VMPgRCO.jpg',
        'Образование
1995 г. — «Терапевт», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач первой категории)
тест: https://medik.kg/doctor/zhumalieva-4/', 'Жумалиева', 'Главный врач', false, 20),
       (21, 'Дмитрий', 'https://medik.kg/media/doctor/Алексеев.JPG',
        'О враче
Специализируется на лечении заболеваний вен: варикозная болезнь, сосудистые «звездочки», тромбофлебит любых локализаций, посттромбофлебитическая болезнь, острые тромбозы и тромбофлебиты (поверхностные и глубокие) и др.
Образование
2014 г. — «Флеболог», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек (Врач высшей категории)
тест: https://medik.kg/doctor/alekseev/', 'Алексеев', 'Главный врач', false, 21),
       (22, 'Анара', 'https://medik.kg/media/doctor/IMG-20191028-WA0000.jpg',
        'Образование
1991 г. — «Физиотерапевт», Кыргызская государственная медицинская академия (КГМА, КГМИ), Бишкек

тест: https://medik.kg/doctor/mambetova-3//', 'Мамбетова ', 'Врач', false, 22);

insert into online_entries(id,is_deleted, online_entry_status, recorded_date, recorded_time, user_name, user_phone_number,
                           user_email,
                           clinic_service_id,expert_id, user_id)
values (1, true,'подвержден', '2023-02-28', '20:15:39', 'Syimyk Bayaliev', '+996507170771', 'syimyk@gmail.com', 2, 2,2),
       (2,true, 'отменен', '2023-11-08 ', '11:10:20', 'Sapar Degenbaev', '+996700652665', 'sapar85@gmail.com', 2,2, 3);

insert into results(id, result_file,date, result_order_number, user_id)
values (1, '125', '2022-12-12', 'HFBVYYN47SW9QDYCDFY', 2),
       (2, '126', '2022-09-09','DSIJ324AKSIONERNVKE', 5);



insert into user_clinic_service(user_id, clinic_service_id)
values (2, 4),
       (5, 17);



