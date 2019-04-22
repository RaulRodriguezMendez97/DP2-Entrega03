start transaction;

use `Acme-Hacker-Rank`;

revoke all privileges on `Acme-Hacker-Rank`.*from 'acme-user'@'%';

revoke all privileges on `Acme-Hacker-Rank`.*from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-Hacker-Rank`;

commit;