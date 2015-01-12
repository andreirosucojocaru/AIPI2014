create 'publishing_house', {NAME=>'identification'}, {NAME=>'address'}

create 'series', {NAME=>'detail'}

create 'genre', {NAME=>'detail'}

create 'book', {NAME=>'identification'}, {NAME=>'version'}, {NAME=>'group'}, {NAME=>'inventory'}

create 'writer', {NAME=>'appellation'}, {NAME=>'biography'}

create 'author', {NAME=>'reference'}

create 'supply_order', {NAME=>'identification'}, {NAME=>'situation'}, {NAME=>'producer'}

create 'supply_order_detail', {NAME=>'reference'}, {NAME=>'content'}

create 'user', {NAME=>'appellation'}, {NAME=>'contact'}, {NAME=>'category'}, {NAME=>'authentication'}

create 'invoice', {NAME=>'identification'}, {NAME=>'situation'}, {NAME=>'consumer'}

create 'invoice_detail', {NAME=>'reference'}, {NAME=>'content'}

create 'statistics', {NAME=>'expense'}
