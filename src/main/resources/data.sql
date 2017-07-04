insert into listener(name, password, enabled)
values('guest', '$2a$10$oVDTiNn.iqp0LAd0jVtbM.KRZiTjKnbpVS7yJHmHY1sNcfpE81ByK', true),
('jezzer', '$2a$10$I0geSczSwmNo2fQEKc1CguG3p7r8wDospZHGhtPeqMjfyrbr3yziS', true),
('jezzer2', '$2a$10$cSdkzzwgbrTdh.zGec7xNOgGEQdLUISP9Si9uPybxdoHnvrsLFM5K', true);

insert into mix(discogs_api_url, discogs_web_url, title, recorded, comment)
values ('http://api.discogs.com/lists/334571', 'http://www.discogs.com/lists/140517-Zyron-Live-on-ISFM/334571', '(140517) Zyron Live on ISFM', '2014-05-17', null),
(null, null, 'DJ Zyron Live on ISFM 2015-03-07', '2015-03-07', null),
(null, null, 'Disque 72 Buyers Mix Club', null, null),
(null, null, 'DJ Zyron Live on ISFM 2017-06-10', '2017-06-10', null),
(null, null, 'Haunted House', null, null),
(null, null, 'Discogs Radio May 2006', '2006-05-27', null),
(null, null, 'DJ Zyron Live on ISFM 2016-09-03', '2016-09-03', null),
('http://api.discogs.com/lists/144332', 'https://www.discogs.com/lists/110219-Zyron-Live-on-ISFM/144332', 'Zyron Saturday Stream 2011-02-19', '2011-02-19', null),
('http://api.discogs.com/lists/143593', 'https://www.discogs.com/lists/111119-Zyron-Live-on-ISFM/143593', 'Zyron Saturday Stream 2011-11-19', '2011-11-19', 'An Interstate FM live stream'),
(null, null, 'DJ Zyron Live on ISFM 2011-04-23', '2011-04-23', 'An Interstate FM live stream'),
(null, null, 'DJ Zyron Live on ISFM 2014-10-18', '2014-10-18', 'An Interstate FM live stream');