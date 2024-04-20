-- SELECT date();

-- SELECT time();

-- SELECT datetime();

-- SELECT strftime('%d/%m/%Y') AS 'Data';

--SELECT strftime('%H:%M:%S') AS 'Hora';

-- SELECT strftime('%d/%m/%Y %H:%M:%S') AS 'Data Hora';

-- SELECT datetime('now', 'localtime') as 'Data e Hora Local';

-- SELECT time('now', 'localtime') as 'Hora Local';

SELECT datetime(CURRENT_TIMESTAMP, 'localtime');

