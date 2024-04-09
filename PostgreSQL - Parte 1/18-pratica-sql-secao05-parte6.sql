-- Data Atual
-- SELECT CURRENT_DATE AS "Data Atual";

-- Hora Atual
-- SELECT CURRENT_TIME AS "Hora Atual";

-- Calcular data futura
-- SELECT CURRENT_DATE + INTERVAL '3 DAY' AS "Data Vencimento";

-- SELECT CURRENT_DATE + INTERVAL '1 MONTH' AS "Data Vencimento";

-- SELECT CURRENT_DATE + INTERVAL '2 YEAR' AS "Data Vencimento";

-- Calcular data passada 
-- SELECT CURRENT_DATE - INTERVAL '3 DAY' AS "Data Matricula";

-- SELECT CURRENT_DATE - INTERVAL '1 MONTH' AS "Data Matricula";

-- SELECT CURRENT_DATE - INTERVAL '2 YEAR' AS "Data Matricula";

-- Diferenca entre datas

-- Em anos
-- SELECT DATE_PART('year', '2019-01-01'::date) - DATE_PART('year', '2011-10-02'::date) AS "Em anos";
-- SELECT DATE_PART('year', CURRENT_DATE::date) - DATE_PART('year', '2011-10-02'::date) AS "Em anos";

-- Em meses
-- SELECT (DATE_PART('year', '2019-01-01'::date) - DATE_PART('year', '2011-10-02'::date)) * 12 +
--	(DATE_PART('month', '2019-01-01'::date) - DATE_PART('month', '2011-10-02'::date)) AS "Em meses";

-- Em semanas
-- SELECT TRUNC(DATE_PART('day', '2019-01-01'::timestamp - '2011-12-22'::timestamp)/7) AS "Em Semanas";

-- Em dias
-- SELECT DATE_PART('day', '2019-01-01'::timestamp - '2011-10-02'::timestamp) AS "Em dias";

-- Em Horas
-- SELECT DATE_PART('day', '2019-01-01 11:55'::timestamp - '2019-01-01 09:55'::timestamp) * 24 + 
--       DATE_PART('hour', '2019-01-01 11:55'::timestamp - '2019-01-01 09:55'::timestamp) AS "Em horas";

-- Em Minutos
-- SELECT (DATE_PART('day', '2019-01-01 11:55'::timestamp - '2019-01-01 09:55'::timestamp) * 24 + 
--         DATE_PART('hour', '2019-01-01 11:55'::timestamp - '2019-01-01 09:55'::timestamp)) * 60 +
--         DATE_PART('minute', '2019-01-01 11:55'::timestamp - '2019-01-01 09:55'::timestamp) AS "Em minutos";


-- Em Segundos
-- SELECT ((DATE_PART('day', '2019-01-01 11:55'::timestamp - '2019-01-01 09:55'::timestamp) * 24 + 
--         DATE_PART('hour', '2019-01-01 11:55'::timestamp - '2019-01-01 09:55'::timestamp)) * 60 +
--         DATE_PART('minute', '2019-01-01 11:55'::timestamp - '2019-01-01 09:55'::timestamp)) * 60 +
--         DATE_PART('second', '2019-01-01 11:55'::timestamp - '2019-01-01 09:55'::timestamp) AS "Em segundos";

-- Formatando data no PostgreSQL
-- SELECT TO_CHAR(CURRENT_DATE, 'dd/mm/YYYY') AS "Data Atual";

-- SELECT TO_CHAR(NOW(), 'dd/mm/YYYY HH24:MM:SS') AS "Data Hora Atual";

-- DAYNAME PostgreSQL

-- Com inicial maiúscula
-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'Day') AS "Dia da Semana";

-- Com short maiúscula
-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'Dy') AS "Dia da Semana";

-- Com inicial minúscula
-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'day') AS "Dia da Semana";

-- Com short minúscula
-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'dy') AS "Dia da Semana";

-- Tudo maiúscula
-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'DAY') AS "Dia da Semana";

-- Mes tudo maiúsculo
-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'MONTH') AS "Mes";

-- Mes tudo minúsculo
-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'month') AS "Mes";

-- Mes iniciais minúsculo
-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'mon') AS "Mes";

-- Mes iniciais maiúsculo
-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'Mon') AS "Mes";

-- SHOW lc_time;

-- SET lc_time='pt_BR.UTF8';

-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'TMDay') AS "Dia da Semana";

-- SELECT TO_CHAR(CURRENT_TIMESTAMP, 'TMMonth') AS "Mes";

-- Extraindo partes de uma data
-- SELECT EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AS "Numero do Mes";

-- SELECT EXTRACT(DAY FROM CURRENT_TIMESTAMP) AS "Dia do Mes";

-- SELECT EXTRACT(YEAR FROM CURRENT_TIMESTAMP) AS "Ano";

-- SELECT EXTRACT(WEEK FROM CURRENT_TIMESTAMP) AS "Numero da Semana";

-- SELECT NOW() AS Data_hora;

-- SELECT EXTRACT(HOUR FROM CURRENT_TIMESTAMP) AS hora;

-- SELECT EXTRACT(Minute FROM CURRENT_TIMESTAMP) AS Minuto;

-- SELECT EXTRACT(Second FROM CURRENT_TIMESTAMP) AS Segundo;

-- SELECT EXTRACT(millisecond FROM CURRENT_TIMESTAMP) AS MiliSegundo;

-- Convertendo de segundos para hora
--SELECT TO_CHAR((71741.733159 || 'seconds')::interval, 'HH24:MM:SS') AS "Tempo Total";

-- SELECT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP::time) AS Em_Segundos;