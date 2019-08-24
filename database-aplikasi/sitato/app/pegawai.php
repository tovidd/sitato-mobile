<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class pegawai extends Model
{
    use SoftDeletes;
    protected $table= 'pegawai';
    protected $primaryKey= 'id_pegawai';
    public $timestamps= true;
    protected $fillable= [
        'id_role',
        'id_cabang',
        'no_telepon_pegawai',
        'nama_pegawai',
        'alamat_pegawai',
        'gaji_pegawai',
        'username_pegawai',
        'sandi_pegawai'
    ];
}
