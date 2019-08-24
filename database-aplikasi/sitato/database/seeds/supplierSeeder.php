<?php

use Illuminate\Database\Seeder;
use sitato\supplier;

class supplierSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        supplier::create([
            'nama_supplier'=> 'Busi motor jaya',
            'no_telepon_supplier'=> '081212445566',
            'alamat_supplier'=> 'Jalan babarsari nomor 1 blok 1B',
            'nama_sales'=> 'Jonathan',
        ]);
        supplier::create([
            'nama_supplier'=> 'Ban motor jaya',
            'no_telepon_supplier'=> '089595001122',
            'alamat_supplier'=> 'Jalan babarsari nomor 2 blok 2B',
            'nama_sales'=> 'Sari',
        ]);
        supplier::create([
            'nama_supplier'=> 'Lampu motor jaya',
            'no_telepon_supplier'=> '081313990011',
            'alamat_supplier'=> 'Jalan babarsari nomor 3 blok 3B',
            'nama_sales'=> 'Hana',
        ]);
    }
}
