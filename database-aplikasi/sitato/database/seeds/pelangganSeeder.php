<?php

use Illuminate\Database\Seeder;
use sitato\pelanggan;

class pelangganSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        pelanggan::create([
            'nama_pelanggan'=> 'Yafet',
            'no_telepon_pelanggan'=> '08111222333',
            'alamat_pelanggan'=> 'Jalan tambak bayan nomor 1 blok 1A'
        ]);
        pelanggan::create([
            'nama_pelanggan'=> 'Ray',
            'no_telepon_pelanggan'=> '08222333444',
            'alamat_pelanggan'=> 'Jalan tambak bayan nomor 1 blok 2A'
        ]);
        pelanggan::create([
            'nama_pelanggan'=> 'Guntur',
            'no_telepon_pelanggan'=> '08333444555',
            'alamat_pelanggan'=> 'Jalan tambak bayan nomor 1 blok 3A'
        ]);
        pelanggan::create([
            'nama_pelanggan'=> 'Aga',
            'no_telepon_pelanggan'=> '08444555666',
            'alamat_pelanggan'=> 'Jalan tambak bayan nomor 1 blok 4A'
        ]);
        pelanggan::create([
            'nama_pelanggan'=> 'Yulius',
            'no_telepon_pelanggan'=> '08555666777',
            'alamat_pelanggan'=> 'Jalan tambak bayan nomor 1 blok 5A'
        ]);
    }
}
