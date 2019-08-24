<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class cabang extends Model
{
    protected $table= 'cabang';
    protected $primaryKey= 'id_cabang';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'no_telepon_cabang',
        'nama_cabang',
        'alamat_cabang'
    ];
}
