CREATE TABLE IF NOT EXISTS `levels_player` (
    `player_id`     INT(4)       NOT NULL AUTO_INCREMENT,
    `player_uuid`   CHAR(36)     NOT NULL UNIQUE,
    `level`         INT(4)       NOT NULL,
    `experience`    INT(4)       NOT NULL,
	`money`         INT(4)       NOT NULL,
    `unlocked_rewards`    VARCHAR(100) NOT NULL,
PRIMARY KEY (`player_id`),
KEY (`player_uuid`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = latin1;
