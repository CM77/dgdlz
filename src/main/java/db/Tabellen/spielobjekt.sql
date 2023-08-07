-- Spielobjekt

create table textadventure.spielobjekt (
id bigint primary key,
spielobjekt_typ int,
pos_x int,
pos_y int,
name varchar,
gewicht int,
mitgenommen bool,
beschaffenheit varchar
	);

-- Werte für "Raum"
insert into textadventure.spielobjekt(id, spielobjekt_typ, pos_x, pos_y, name, gewicht, mitgenommen, beschaffenheit)
values 
(1, 1, 1, 0, 'Marienplatz', null, null, 'Das Münchner Rathaus erhebt sich vor dir. Touristen warten auf das Glockenspiel. Hinter dir ist die Mariensäule. Du fragst dich unablässig, ob du den Herd daheim ausgemacht hast...'),
(2, 1, 0, 0, 'Stachus', null, null, 'Vor dir ist der Stachus-Brunnen, der unablässig Fontänen ausspuckt. Auf den Steinquadern rund um den Brunnen sitzen einige Leute und halten ihre Füße in die kühle Gischt. Hinter dir ist ein McDonalds, vor dem einige gebasecappte Halbstarke aus einem Ghettoblaster HipHop hören. Ein großes Kino fällt dir auf. Vielleicht solltest du dir da mal wieder nen richtigen zum Heulen schönen Schmachtfetzen reinziehen...'),
(3, 1, 0, 1, 'Kapellenstrasse', null, null, 'Es ist eine schmale Straße. Niemand ist zu sehen. Ob du deine Oma anrufen sollst, um sie um Beistand zu bitten?')
(4, 1, 0, 3, 'Keller', null, null, 'Es ist ein düsterer Keller ohne Fenster, der noch aus früheren Jahrhunderten stammen muss, so tief wie er gelegen ist. Die Wände bestehen aus grob gehauenem Stein. Eine alte Glühbirne spendet gelbliches, fahles Licht. Wenigstens wird nicht viel CO2 in so einer Trollhöhle erzeugt... Hast du das gerade wirklich laut gedacht?'),
(5, 1, 0, 2, 'Wendeltreppe', null, null, 'Eine in grobem Stein gehauene Wendeltreppe führt ganz schön tief hinunter. Seltsamerweise sind brennende Fackeln an den Wänden angebracht. Du spürst einen leichten Luftzug und es scheint dir, als hörest du ein merkwürdiges leises Dröhnen von ganz unten.')
;

-- Werte für "Gegenstand"
insert into textadventure.spielobjekt(id, spielobjekt_typ, pos_x, pos_y, name, gewicht, mitgenommen, beschaffenheit)
values 
(6, 2, 1, 0, 'Apfel', 1, false, 'Es ist ein leckerer Apfel.'),
(7, 2, 0, 0, 'Samuraischwert', 12, false, 'Es ist ein altes japanisches Samuraischwert, und die extrem scharfe Klinge glänzt in der Sonne.')
;
